<template>
  <p>확인을 위해 여기 넣었다</p>
  <p>컴포넌트로 옮겨서 사용하도록!!</p>
  <div class="ticket">
  <div class="ticket__content">
    <div class="bg__img"><img class="bg__img" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ93_QLM1OJsDpjscuN1w-SekUokj4Vi9i0eA&usqp=CAU" alt=""></div>
    <p class="ticket__text">2022/07/22</p>
    <p>회의한날 넣어주기</p>
    <p>가슴이웅장해지는웃는남자</p>
    <p>어쩌구저쩌구정보</p>
  </div>
</div>
</template>

<script>
export default {

}
</script>

<style lang="scss">

.ticket {
  position: relative;
  box-sizing: border-box;
  width: 300px;
  height: 600px;
  margin: 150px auto 0;
  padding: 20px;
  border-radius: 10px;
  background: #FBFBFB;
  box-shadow: 2px 2px 15px 0px #4e4c67;
  
  &:before,
  &:after {
    content: '';
    position: absolute;
    left: 5px;
    height: 6px;
    width: 290px;
  }
  
  &:before {
    top: -5px;
    background: radial-gradient(circle, transparent, transparent 50%, #FBFBFB 50%, #FBFBFB 100% ) -7px -8px / 16px 16px repeat-x,
  }
  
  &:after {
    bottom: -5px;
    background: radial-gradient(circle, transparent, transparent 50%, #FBFBFB 50%, #FBFBFB 100% ) -7px -2px / 16px 16px repeat-x,
  }
}

.ticket__content {
  box-sizing: border-box;
  height: 100%;
  width: 100%;
  border: 6px solid #D8D8D8;
}

.bg__img {
  width: 100%;
}

.ticket__text {
  width: 400px;
  font-family: 'Helvetica', 'Arial', sans-serif;
  font-size: 3rem;
  font-weight: 900;
  text-transform: uppercase;
  color: darkred;
  transform: translate(-20px, -150px) rotate(-30deg) ;
}
</style>