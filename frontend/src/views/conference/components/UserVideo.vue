<template>
<div v-if="streamManager" class="container">
	<ov-video class="ovVideo" :stream-manager="streamManager"/>
	<div class="d-flex aho">
		<div class="nametag">{{ clientData.name }}</div>
	</div>
	<div>
		<div class="followabs" v-if="elVisible" @click="followClick">
			<img class="like" src="@/assets/follow.png" 
				style="width:24px; height: 24px; top: 33px; left: 4px; z-index: 1; cursor: pointer;"
				>
		</div>
		<div class="reportabs" v-if="elVisible" @click="reportClick">
			<img class="report" src="@/assets/exclamation-mark.png" 
				style="width:28px; height: 28px; top: 30px; left: 8px; z-index: 1; cursor: pointer;"
				>
		</div>
	</div>
</div>
</template>

<script>
import OvVideo from './OvVideo.vue'
import { useAccounts } from "@/stores/accounts"
import { mapState } from 'pinia'
import secosi from "@/api/secosi"
import axios from "axios"
import Swal from 'sweetalert2'

export default {
	name: 'UserVideo',
	components: {
		OvVideo,
	},
	props: {
		streamManager: Object,
	},
	data(){
		return{
			elVisible: true,
			thisUserInfo: undefined,
			reportInfo: {
				"reportContent": "",
				"reportUserId": ""
			},
		}
	},
	computed: {
		...mapState(useAccounts, ['currentUser']),
		clientData () {
			const { clientData } = this.getConnectionData();
			this.thisUserInfo = clientData.userId
			return clientData
		},
	},
	methods: {
		followClick(){
			if(this.clientData.userId === this.currentUser.userId){
				this.elVisible = false
			}else{
				this.follow(this.clientData.userId)
			}
		},
		follow(id){
			console.log(id)
			const config = {
				headers: {
					Authorization : `Bearer ${localStorage.getItem('token')}`
				}
			}
			console.log(config)
			axios.post(secosi.conferences.following()+`?followingId=${id}&userId=${this.currentUser.userId}`, config) 
				.then(()=> console.log('팔로우가 잘되었다~'))
				.catch(err=> {
					console.error(err.response.data)
					
				})
		},
		report(info){
			const config = {
				headers: {
					Authorization : `Bearer ${localStorage.getItem('token')}`
				}
			}
			axios.post(secosi.conferences.report(), info, config)
			.then(() => console.log('신고가 잘 되었다~'))
			.catch(err => {
				console.error(err.response.data)
			})
		},
		reportClick() {
			if(this.clientData.userId === this.currentUser.userId){
				this.elVisible = false
			}else{
				Swal.fire({
					title: `${this.clientData.name}님을\n 신고 하시겠습니까?`,
					input: 'text',
					inputPlaceholder: '신고사유를 입력하세요.',
					showCloseButton: true,
					confirmButtonText: '신고',
					confirmButtonColor: '#1b3c33',
					inputAttributes: {
						autocapitalize: 'off',
						autocorrect: 'off'
					},
					inputValidator: (input) => {
						if (input) {
							this.reportInfo.reportContent = input
							this.reportInfo.reportUserId = this.clientData.userId
							this.report(this.reportInfo)
							return console.log(this.reportInfo)
						}else{
							return "내용을 작성하세요."
						}
					},
				})
			}
    },
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			console.log(this.streamManager.stream)
			return JSON.parse(connection.data);
		},
	},
}	
</script>
<style scoped>
.ovVideo {
	width: 300px;
	margin-bottom: 1rem;
}
.nametag {
	position: absolute;
	/* left: 3rem; */
	z-index: 1;
	background-color: #1b3c33;
	color: white;
}
.container {
	width: 320px;
}
.aho {
	/* display: flex; */
	position: absolute;
	/* justify-content: space-between; */
	width: 110%;
}
.like {
	margin-left: 3px;
}
</style>