<template>
	<div class="container">
		<div class="d-flex main-box">
			<div class="malan" id="video-container">
				<user-video :stream-manager="publisher" v-on:click="updateMainVideoStreamManager(publisher)"/>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" v-on:click="updateMainVideoStreamManager(sub)"/>
			</div>
				<div class="chat-session">
					<div class="chat">
						<div class="chat-header clearfix">
							<div class="chat-about">
								<div class="chat-with">{{ conferenceTitle }}</div>
							</div>
						</div>
						<div class="chat-history">
							<ul style="list-style: none;" v-for="(message, index) in chattingList" :key="index">
								<li v-if="message.writer === currentUser.name" class="clearfix">
									<div class="message-data align-right">
										<span class="message-data-time" >{{this.possibleEmojis[message.cute]}}</span>
										<span class="message-data-name" >{{message.writer}}</span> 
									</div>
									<div class="message other-message float-right">
										{{ message.text }}
									</div>
								</li>
								<li v-else class="clearfix">
									<div class="message-data">
										<span class="message-data-name">{{message.writer}}</span>
										<span class="message-data-time">{{this.possibleEmojis[message.cute]}}</span>
									</div>
									<div class="message my-message">
										{{ message.text }}
									</div>
								</li>
							</ul>
						</div>
						<div class="chat-message clearfix">
							<textarea v-model="textMessage" @keyup.enter="makeMessage" name="message-to-send" id="message-to-send" placeholder ="채팅을 입력하세요." rows="3"></textarea>     
							<button @click="makeMessage">보내기</button>
						</div>
					</div>
						<div id="session-header">
							<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="방 나가기">
						</div>
					</div>
			</div>
	</div>
</template>

<script>
import  axios from 'axios'
import { OpenVidu } from 'openvidu-browser'
import { useAccounts } from "@/stores/accounts";
import UserVideo from './components/UserVideo.vue'
import { mapState } from 'pinia'
import router from '@/router'
import secosi from "@/api/secosi"

const OPENVIDU_SERVER_URL = 'https://' + window.location.hostname + ':4443'
const OPENVIDU_SERVER_SECRET = "MY_SECRET";


axios.defaults.headers.post['Content-Type'] = 'application/json';

export default {
  name: 'ConferenceDetailView', 
  components:{
    UserVideo,
		// MyVideo
  },
  data(){
    return{
			conferenceTitle: "",
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers:[],
      mySessionId: 'SessionA',
      myUserName : 'Participant' + Math.floor(Math.random() * 100),
			possibleEmojis : [],
			pos_emoji : this.randomEmoji(),
			chattingList : [],
			textMessage: "",
    }
  },
	computed: {
		...mapState(useAccounts, ['currentUser'])
	},
  created() {
		this.setData()
  },
	mounted(){
		this.joinSession()
		this.roomIn(this.mySessionId)
	},
  methods : {
		roomIn(myRoomNum){
			axios.patch(secosi.conferences.join() + `/${myRoomNum}`)
				.then()
				.catch(err => {
					console.error(err.response.data)
				})
		},
		roomOut(index){
			axios.patch(secosi.conferences.out() + `/${this.mySessionId}?person_now=${index}`)
				.then()
				.catch( err => {
					console.error(err.response.data)
				})
		},
		roomClose(roomNum){
			axios.delete(secosi.conferences.create()+`/${roomNum}`)
				.then()
				.catch(err => {
					console.error(err.response.data)
				})
		},
		setData(){
			this.mySessionId = this.$route.params.conferenceid
			this.myUserName = this.currentUser
			this.conferenceTitle = this.$route.params.title
			this.possibleEmojis = [
			'🐀', '🐁', '🐭', '🐹', '🐂', '🐃', '🐄', '🐮', '🐅', '🐆', '🐯', '🐇', '🐐', '🐑', '🐏', '🐴',
			'🐎', '🐱', '🐈', '🐰', '🐓', '🐔', '🐤', '🐣', '🐥', '🐦', '🐧', '🐘', '🐩', '🐕', '🐷', '🐖',
			'🐗', '🐫', '🐪', '🐶', '🐺', '🐻', '🐨', '🐼', '🐵', '🙈', '🙉', '🙊', '🐒', '🐉', '🐲', '🐊',
			'🐍', '🐢', '🐸', '🐋', '🐳', '🐬', '🐙', '🐟', '🐠', '🐡', '🐚', '🐌', '🐛', '🐜', '🐝', '🐞',
			]
		},
		randomEmoji() {
    	var randomIndex = Math.floor(Math.random() * 63)
    	return randomIndex
		},
    joinSession() {
      this.OV = new OpenVidu()
      this.session = this.OV.initSession()

      this.session.on('streamCreated', ({stream}) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber)
      })
			this.session.on('signal:chat', (event) => {
				let messageInfo = event.data.split('.././././.')
				let chattingItem = {text: messageInfo[0], writer: messageInfo[1].replace('.././././.', ''), cute: Number}
				const pos_emoji = parseInt(this.randomEmoji())
				chattingItem.cute = parseInt(pos_emoji)
				this.chattingList = [...this.chattingList, chattingItem]
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
				this.myUserName = this.currentUser
        this.session.connect(token, {clientData: this.myUserName})
          .then(()=> {
						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, 
							videoSource: undefined,
							publishAudio: true,
							publishVideo: true, 
							resolution: '240x150',
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
			if (this.subscribers.length === 0){
				this.roomClose(this.mySessionId)
			}else{
				this.roomOut(this.subscribers.length)
			}
			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;
			router.push({ name: 'ConferenceList' })
			window.removeEventListener('beforeunload', this.leaveSession);      
    },
		makeMessage(){
			this.session.signal({
				data: this.textMessage+'.././././.'+this.currentUser.name,
				to: [],
				type:'chat',
			})
			.then(()=> {
				this.textMessage = ""
			})
			.catch(error => {
				console.error(error)
			})
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
#main{
	width:1400px;
}
.container{
	display: flex;
	margin: 0;
	padding: 0;
	justify-content: space-between;
}
.clearfix, .clearfix:before, .clearfix:after {
  box-sizing: border-box;
	text-decoration: none;
}

.chat {
  width: 340px;
  float:left;
  background: #eef2ed;
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
  color: #434651;
}
  
.chat .chat-header {
	padding: 3px;
	padding-left: 10px;
	border-bottom: 2px solid white;
}
.chat .chat-header .chat-about {
	float: left;
	padding-left: 10px;
	margin-top: 6px;
}

.chat .chat-header .chat-with {
	font-weight: bold;
	font-size: 16px;
}

.chat .chat-history {
	padding: 10px 10px 10px;
	border-bottom: 2px solid white;
	overflow-y: scroll;
	height:460px;
}
    
.chat .chat-history .message-data {
	margin-bottom: 6px;
}
    
.chat .chat-history .message-data-time {
	color: lighten(#92959E, 8%);
	padding-left: 6px;
}
    
.chat .chat-history .message {      
	color: white;
	padding: 4px 20px;
	line-height: 26px;
	font-size: 16px;
	border-radius: 7px;
	margin-bottom: 0px;
	width: 90%;
	position: relative;
}
  
.chat .chat-history .my-message {
	background: #92a0bc;
}
    
.chat .chat-history .other-message {
  background: #1B3C33;
}
.chat-message {
	padding: 10px 30px;
}
.chat-message textarea {
	width: 75%;
	border: none;
	padding: 10px 20px;
	padding-bottom: 0px;
	font: 14px/22px "Lato", Arial, sans-serif;
	margin-bottom: 0px;
	border-radius: 5px;
	resize: none;
}

.chat-message button {
	float: right;
	color: #94C2ED;
	font-size: 16px;
	text-transform: uppercase;
	border: none;
	cursor: pointer;
	font-weight: bold;
	background: #F2F5F8;
	margin-left: 4px;
}
.chat-message button:hover{
  color: darken(#94C2ED, 7%);
} 

.online, .offline, .me {
    margin-right: 3px;
    font-size: 10px;
  }

.align-left {
  text-align: left;
}

.align-right {
  text-align: right;
}

.float-right {
  float: right;
}

.clearfix:after {
	visibility: hidden;
	display: block;
	font-size: 0;
	content: " ";
	clear: both;
	height: 0;
}
.malan {
	column-count: 2;
	margin: 1rem;
}
.chat-session{
	margin-left: 3rem;
	margin-top: 1rem;
}
</style>