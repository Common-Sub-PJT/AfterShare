<template>
<div>히히</div>
	<div id="main-container" class="container">
		<div id="session">
			<div id="session-header">
				<h1 id="session-title">{{ mySessionId }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
			</div>
			<div id="main-video" class="col-md-6">
				<user-video :stream-manager="mainStreamManager"/>
			</div>
			<div id="video-container" class="col-md-6">
				<user-video :stream-manager="publisher" v-on:click="updateMainVideoStreamManager(publisher)"/>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" v-on:click="updateMainVideoStreamManager(sub)"/>
			</div>
		</div>
	</div>
</template>

<script>
import  axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
import UserVideo from './components/UserVideo.vue'
// import secosi from "@/api/secosi"

const OPENVIDU_SERVER_URL = 'https://' + window.location.hostname + ':4443'
const OPENVIDU_SERVER_SECRET = "MY_SECRET";


axios.defaults.headers.post['Content-Type'] = 'application/json';

export default {
  name: 'ConferenceDetailView', 
  components:{
    UserVideo,
  },
  data(){
    return{
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers:[],
      mySessionId: this.$route.params.conferenceId,
      myUserName : this.$route.params.userId + Math.floor(Math.random() * 100),
    }
  },
  created() {
    this.joinSession()
  },
  methods : {
    joinSession() {
      // openvidu client 객체 가져오기
      this.OV = new OpenVidu()

      // init a session
      this.session = this.OV.initSession()

      this.session.on('streamCreated', ({stream}) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber)
      })

      this.session.on('streamDestroyed', ({stream}) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0)
        if (index >= 0) {
          this.subscribers.splice(index, 1)
        }
      })
      
      this.session.on('exception', ({ exception }) => {
        console.warn(exception)
      })

      this.getToken(this.mySessionId).then(token => {
        this.session.connect(token, {clientDate: this.myUserName})
          .then(()=> {
						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, 
							videoSource: undefined,
							publishAudio: true,
							publishVideo: true, 
							resolution: '640x480',
							frameRate: 30,
							insertMode: 'APPEND',	
							mirror: false 
						});

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						this.session.publish(this.publisher);
          })
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
      })
      window.addEventListener('beforeunload', this.leaveSession)
    },
    leaveSession () {
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);      
    },

    updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},



   	getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},
  }
}
</script>

<style scoped>
</style>