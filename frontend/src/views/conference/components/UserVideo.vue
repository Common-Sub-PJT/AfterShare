<template>
<div v-if="streamManager">
	<div v-if="elVisible" @click="followClick">
		<img class="like" src="@/assets/follow.png" 
			style="width:24px; height: 24px; top: 33px; left: 4px; z-index: 1; cursor: pointer;"
			>
	</div>
	<div v-if="elVisible" @click="reportClick">
		<img class="report" src="@/assets/exclamation-mark.png" 
			style="width:30px; height: 30px; top: 30px; left: 8px; z-index: 1; cursor: pointer;"
			>
	</div>
	<div><p style="top:30px;">{{ clientData.name }}</p></div>
	<ov-video :stream-manager="streamManager"/>
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
			// this.thisUserInfo
			// this
			return clientData
		},
	},
	methods: {
		// elShow(){
		// 	console.log(this.currentUser.userId)
		// 	if(this.thisUserId !== this.currentUser.userId){
		// 		this.elVisible = false
		// 	}
		// },
		followClick(){
			if(this.clientData.userId === this.currentUser.userId){
				this.elVisible = false
			}else{
				this.follow(this.clientData.userId)
			}
		},
		follow(id, my){
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
</style>