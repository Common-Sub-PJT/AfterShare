<template>
  <LoginModal></LoginModal>
  
  <div>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#login-modal">
      Login
    </button>
  </div>
</template>

<script>
import LoginModal from '@/views/accounts/components/LoginModal.vue'

export default {
  name: 'LoginButton',
  components: {
    LoginModal,
  },
}
</script>

<style>
</style>
