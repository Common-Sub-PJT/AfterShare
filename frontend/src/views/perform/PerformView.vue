<template>
<div class="box">
  <div class="search-box">
    <div class="dropdown">
      <ul>
        도/시 선택
        <li>서울시</li>
        <li>어쩌구</li>
      </ul>
      <ul>
        시/군/구 선택
        <li>종로구</li>
        <li>어쩌구</li>
      </ul>
    </div>
    <div class="search-field">
      <input type="text" name="" id="" placeholder="제목검색">
      <i class="fa-solid fa-magnifying-glass"></i>
    </div>
  </div>
  <nav>
    <ul class="nav">
      <li>전체</li>
      <li>뮤지컬</li>
      <li>어쩌구</li>
      <li>저쩌구</li>
      <li>어쩌구</li>
      <li>저쩌구</li>
    </ul>
  </nav>
</div>
<div class="card">
  <div v-for="perform in performList" :key="perform.mt20id" class="card" style="width: 18rem;">
    <router-link :to="{ name: 'PerformDetail', params: {mt20id: perform.mt20id} }">
      <img class="card-img-top" :src='`${perform.poster}`' alt="Card image cap">
      <div class="card-body">
        <p class="card-text">{{perform.prfnm}}  [{{perform.genrenm}}]</p>
        <p class="card-text">{{perform.prfpdfrom}} ~ {{perform.prfpdto}}</p>
        <p class="card-text">{{perform.fcltynm}}</p>
      </div>
    </router-link>
  </div>
  <infinite-loading @infinite="load"></infinite-loading>
</div>
</template>

<script>
// import { loadPerform } from '@/stores/performs'
import axios from 'axios'
import secosi from "@/api/secosi"
import { defineComponent } from 'vue'

export default defineComponent ({
  name:'PerformView',
  data() {
    return {
      performList: [],
    }
  },
  methods: {
    fetchPerforms() {
      axios.get(secosi.performs.performs())
        .then(res => {
          this.performList=res.data
        })
    },
  },
  created() {
    this.fetchPerforms()
  },

  // setup() {
  //   onBeforeMount(() => {
  //     axios.get(secosi.performs.performs())
  //       .then(res => {
  //           this.performList=res.data
  //           console.log(res);
  //           console.log(this.performList);
  //         })

  //   axios.get(secosi.performs.performs())
  //     .then(res => {
  //       this.performList=res.data
  //       console.log(res)
  //     })
  //   return {
  //     performList
  //   }
  // }
  // methods: {
  //   fetchPerforms() {
  //     axios.get(secosi.performs.performs())
  //       .then(res => {
  //         this.performList=res.data
  //         console.log(res.data);
  //       })
  //       // .catch(err => {
  //       //   console.error(err.response.data)
  //       // })
  //   }
  // },

  // setup(){
    // const performList = reactive({
    //   performs: [],
    // })
    // axios.get(secosi.performs.performs())
    //   .then(res => {
    //     self.performList=res.data
    //     console.log(res)
    //   })
    // return {
    //   performList
    // }
    // const fetchPerform = () => {
    //   axios.get(secosi.performs.performs())
    //     .then(res => {
    //       this.performs=res.data
    //     })
    // }
    // fetchPerforms() {
    //   axios.get(secosi.performs.performs())
    //     .then(res => {
    //       // this.performList=res.data
    //       console.log(res);
    //     })
    //     .catch(err => {
    //       console.error(err.response.data)
    //     })
    // }
    // const performShow = function performShow() {
    //   this.fetchPerforms()
    // }
    // const performGet = performList.fetchPerforms()
    // const performShow = performList.performs
    // let page = 0
    // const load = async $state => {
    //   page++;
    //   console.log('----------------------------');
    //   try {
    //     const response = await fetchPerforms()
    //     console.log('2222222222222222222222222222222');
    //     if (response.length < 10){
    //       $state.complete()
    //       console.log('3333333333333333333333333333333');
    //     }  else {
    //       performs.value.push(...response)
    //       $state.loaded()
    //       console.log('44444444444444444444444444');
    //     }
    //   } catch (error) {
    //     $state.error()
    //   }
    // }

    // return {
    //   // performList,

    //   // fetchPerforms,
    //   // performGet,
    //   // performShow,
    //   // load,
    //   // page
    // }

  // },

})
</script>

<style lang="scss" scoped>
.box {
  border: 3px solid #1b3c33;
  border-radius: 5px;
}
.search-box {
  display: flex;
}
.dropdown {
  display: flex;
}
li {
  list-style: none;
}
a {
  text-decoration: none;
  color: black;
}
</style>