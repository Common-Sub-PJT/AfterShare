

<!-- 필요없는 vue -->



<template>
<LoginModal></LoginModal>
  <div>
    <h1 class="fw-bold m-3">Login</h1>
    <p>This is Login page</p>
    <LoginButton></LoginButton>
  </div>
</template>

<script>
import LoginButton from '@/views/accounts/components/LoginButton.vue'
import LoginModal from '@/views/accounts/components/LoginModal.vue'

export default {
  name: 'LoginView',
  components: {
    LoginButton,
    LoginModal,
  },
}
</script>

<style>
</style>
