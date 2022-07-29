<template>
  <div>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#signup-modal">
      Signup
    </button>
    <SignupModal></SignupModal>
  </div>
</template>

<script>
import SignupModal from '@/views/accounts/components/SignupModal.vue'
export default {
  name: 'SignupButton',
  components: {
    SignupModal,
  },
}
</script>

<style>
</style>
