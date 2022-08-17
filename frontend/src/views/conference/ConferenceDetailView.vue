<template>
	<div id="main-container" class="container">
		<div id="session">
			<div id="session-header">
				<h1 id="session-title">{{ conferenceTitle }}</h1>
				<input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session">
			</div>
			<div id="video-container" class="col-md-6">
				<div>이거는 내 화면</div>
				<user-video :stream-manager="publisher" v-on:click="updateMainVideoStreamManager(publisher)"/>
				<div>이 밑은 다른 참여자 화면</div>
				<user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" v-on:click="updateMainVideoStreamManager(sub)"/>
			</div>
		</div>

    <div id="chat-session">
      <h1 id="chat-session-header"></h1>
      <div class="content">
        <div class="messages" id="chat"></div>
        <form class="footer" onsubmit="return false;">
          <input type="text" id="mensaje" placeholder="Tu mensaje..." />
          <button type="submit" @click="mandar_mensaje">Enviar</button>
        </form>
      </div>

      <template data-template="message">
        <div class="message">
          <div class="message__name"></div>
          <div class="message__bubble"></div>
        </div>
      </template>
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
// import '@/views/conference/conference.css'

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
			conferenceTitle: this.$route.params.title,
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers:[],
      mySessionId: 'SessionA',
      myUserName : 'Participant' + Math.floor(Math.random() * 100),
			possibleEmojis : [],
			// emoji : this.possibleEmojis[this.pos_emoji],
			pos_emoji : this.randomEmoji(),
			chattingList : [],
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
			this.possibleEmojis = [
			'🐀', '🐁', '🐭', '🐹', '🐂', '🐃', '🐄', '🐮', '🐅', '🐆', '🐯', '🐇', '🐐', '🐑', '🐏', '🐴',
			'🐎', '🐱', '🐈', '🐰', '🐓', '🐔', '🐤', '🐣', '🐥', '🐦', '🐧', '🐘', '🐩', '🐕', '🐷', '🐖',
			'🐗', '🐫', '🐪', '🐶', '🐺', '🐻', '🐨', '🐼', '🐵', '🙈', '🙉', '🙊', '🐒', '🐉', '🐲', '🐊',
			'🐍', '🐢', '🐸', '🐋', '🐳', '🐬', '🐙', '🐟', '🐠', '🐡', '🐚', '🐌', '🐛', '🐜', '🐝', '🐞',
			]
			// console.log(this.possibleEmojis)
		},
		randomEmoji() {
			console.log(this.possibleEmojis)
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
				console.log(event.data)
				// console.log('진짜 이러지마...')
				// var mensaje = event.data.split(".././././.")
				let chattingItem = {text: event.data, writer: this.currentUser.name, cute: Number}
				// chattingItem.text = mensaje[0]
				// chattingItem.writer = mensaje[1]
				// chattingItem.cute = parseInt(mensaje[2])
				// this.chattingList.push(chattingItem)
				// console.log(this.chattingList.length)
				// console.log(this.chattingList)
				// const template = document.querySelector('template[data-template="message"]')
				// const nameEl = template.textContent.querySelector('.message__name')

				const pos_emoji = parseInt(this.randomEmoji())
				chattingItem.cute = parseInt(pos_emoji)
				// this.chattingItem.push(chattingItem)
				console.log(this.chattingList)
				this.chattingList = [...this.chattingList, chattingItem]
				console.log(this.chattingList)
			// var mensaje = document.getElementById("mensaje").value
			// // console.log(mensaje)
			// document.getElementById("mensaje").value = ""
			// mensaje = mensaje+".././././."+this.myUserName.name+".././././."+pos_emoji
				// if (mensaje[1] || this.possibleEmojis[parseInt(mensaje[2])]){
				// 	nameEl.innerText = this.possibleEmojis[parseInt(mensaje[2])] + ' ' + mensaje[1]
				// }
				// template.content.querySelector('.message__bubble').innerText = mensaje[0]
        // const clone = document.importNode(template.content, true)
        // const messageEl = clone.querySelector('.message')

				// if (event.from.connectionId === this.session.connection.connectionId){
				// 	messageEl.classList.add('message--mine')
				// } else {
				// 	messageEl.classList.add('message--theres')
				// }

				// const messagesEl = document.querySelector('.messages')
				// messagesEl.appendChild(clone)
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
						document.getElementById("chat-session-header").innerText=this.mySessionId

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
		mandar_mensaje(){
			var mensaje = document.getElementById("mensaje").value
			this.session.signal({
				data: mensaje,
				to: [],
				type:'chat',
			})
			.then(()=> {
				console.log("Mensaje enviado")
			})
			.catch(error => {
				console.error(error)
			})
		},

    updateMainVideoStreamManager (stream) {
			if (this.mainStreamManager === stream) return;
			this.mainStreamManager = stream;
		},



// getToken 함수들

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

<style>


</style>