<template>
  <header>
    <!-- Sidebar -->
    <nav
        id="sidebarMenu"
        class="collapse d-lg-block sidebar collapse bg-white"
        >
      <div class="position-sticky">
        <div class="list-group list-group-flush mx-3 mt-4">
          <router-link to="/mypage/:userId"
            class="list-group-item list-group-item-action py-2 ripple"
            >
            <div class="profile">
              <img src="https://1.bp.blogspot.com/-vhmWFWO2r8U/YLjr2A57toI/AAAAAAAACO4/0GBonlEZPmAiQW4uvkCTm5LvlJVd_-l_wCNcBGAsYHQ/s16000/team-1-2.jpg" alt="프사">
              <h5>닉네임</h5>
              <button class="btn btn-secondary">My Page</button>
            </div>
          </router-link>
          <router-link to="/conferencelist"
            class="list-group-item list-group-item-action py-2 ripple"
            >
            <i class="fas fa-chart-area fa-fw me-3"></i
              ><span>뒷풀이방</span>
          </router-link>
          <router-link to="/login"
            class="list-group-item list-group-item-action py-2 ripple"
            >
            <i class="fas fa-chart-area fa-fw me-3"></i
              ><span>Login</span>
          </router-link>
          <router-link to="/signup"
            class="list-group-item list-group-item-action py-2 ripple"
            >
            <i class="fas fa-chart-area fa-fw me-3"></i
              ><span>Signup</span>
          </router-link>
          <router-link to="/perform/:performId"
            class="list-group-item list-group-item-action py-2 ripple"
            >
            <i class="fas fa-chart-area fa-fw me-3"></i
              ><span>Perform</span>
          </router-link>
          <router-link to="/history/:userId"
            class="list-group-item list-group-item-action py-2 ripple"
            >
            <i class="fas fa-chart-area fa-fw me-3"></i
              ><span>History</span>
          </router-link>
        </div>
      </div>
    </nav>
    <nav
        id="main-navbar"
        class="navbar navbar-expand-lg navbar-light bg-white fixed-top"
        >
      <!-- Container wrapper -->
      <div class="container-fluid">
        <!-- Toggle button -->
        <button
                class="navbar-toggler"
                type="button"
                data-mdb-toggle="collapse"
                data-mdb-target="#sidebarMenu"
                aria-controls="sidebarMenu"
                aria-expanded="false"
                aria-label="Toggle navigation"
                >
          <i class="fas fa-bars"></i>
        </button>

        <!-- Brand -->
        <router-link class="navbar-brand" to="/">
          <img
              src="https://mdbootstrap.com/img/logo/mdb-transaprent-noshadows.png"
              height="30"
              alt=""
              loading="lazy"
              class="m-2"
              />
        </router-link>
      </div>
    </nav>
  </header>
  <main style="margin-top: 58px; height: auto;">
    <div class="container pt-4">
      <RouterView/>
    </div>
  </main>
</template>
<script>
</script>
<style scoped>
body {
  background-color: #fbfbfb;
}
@media (min-width: 991.98px) {
  main {
    padding-left: 240px;
  }
}

/* Sidebar */
.sidebar {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  padding: 58px 0 0; /* Height of navbar */
  box-shadow: 0 2px 5px 0 rgb(0 0 0 / 5%), 0 2px 10px 0 rgb(0 0 0 / 5%);
  width: 240px;
  z-index: 600;
}

@media (max-width: 991.98px) {
  .sidebar {
    width: 100%;
  }
}
.sidebar .active {
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgb(0 0 0 / 16%), 0 2px 10px 0 rgb(0 0 0 / 12%);
}

.sidebar-sticky {
  position: relative;
  top: 0;
  height: calc(100vh - 48px);
  padding-top: 0.5rem;
  overflow-x: hidden;
  overflow-y: auto;
}
.profile {
    margin-bottom: 30px;
    text-align: center;
}
.profile img{
    display: block;
    width: 70px;
    height: 70px;
    border-radius: 50%;
    margin: 0 auto;
}
.profile h3{
    color: #605b5b;
    margin: 10px 0 5px;
}
</style>