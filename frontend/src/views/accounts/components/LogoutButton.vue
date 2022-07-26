<template>
  <div>
    <button type="button" class="btn btn-secondary" @click="logout">
      Logout
    </button>
  </div>
</template>

<script>

export default {
  name: 'LogoutButton',
  components: {
  },
  methods: {
    logout(event) {
      event.preventDefault()
      // localStorage.removeItem('token')
      this.$router.push({ name: 'Home' })
    }
  }
}
</script>

<style>
</style>
