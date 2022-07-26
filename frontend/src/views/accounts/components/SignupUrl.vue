<template>
  <div>
    <a href="#" data-bs-toggle="modal" data-bs-target="#signup-modal">
      Signup
    </a>
    <SignupModal></SignupModal>
  </div>
</template>

<script>
import SignupModal from '@/views/accounts/components/SignupModal.vue'
export default {
  name: 'SignupUrl',
  components: {
    SignupModal,
  },
}
</script>

<style>
</style>
