<template>
  <div class="page">
    <header class="header" style=" margin-top: 0;">
      <h1>뒤풀이어</h1>
      	<form class="search-form">
				<input type="search">
				<input type="submit" value="찾기">
			</form>
    </header>
    <section class="primary"><RouterView /></section>

    <div class="sidebar secondary secondary-a">
      <!-- profile -->
      <Router-link to="/">
          <!-- <img style="width:15rem; height:auto; padding:0; margin:3;" src="@/assets" alt=""> -->
          <p>Home</p>
      </Router-link>
      <div class="profile">
          <img src="https://1.bp.blogspot.com/-vhmWFWO2r8U/YLjr2A57toI/AAAAAAAACO4/0GBonlEZPmAiQW4uvkCTm5LvlJVd_-l_wCNcBGAsYHQ/s16000/team-1-2.jpg" alt="프사">
          <h3>닉네임</h3>
          <RouterLink to="/mypage" class="text-decoration-none">
              <p>마이페이지</p>
          </RouterLink>
      </div>
      <ul>
        <li>
          <a href="#" class="active">
            <span class="icon"><i class="fas fa-home"></i></span>
            <RouterLink to="/"><span class="item">Home</span></RouterLink>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon"><i class="fas fa-desktop"></i></span>
            <RouterLink to="/login">
              <span class="item">Login</span>
            </RouterLink>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon"><i class="fas fa-user-friends"></i></span>
            <span class="item"><RouterLink to="/signup">Signup</RouterLink></span>
          </a>
        </li>
        <li>
          <a href="#">
              <span class="icon"><i class="fas fa-database"></i></span>
              <span class="item"><RouterLink to="/conferencelist">뒷풀이방</RouterLink></span>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon"><i class="fas fa-tachometer-alt"></i></span>
            <span class="item"><RouterLink to="/history/:userId">history</RouterLink></span>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon"><i class="fas fa-chart-line"></i></span>
            <span class="item">Reports</span>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon"><i class="fas fa-user-shield"></i></span>
            <span class="item">Admin</span>
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon"><i class="fas fa-cog"></i></span>
            <span class="item">Settings</span>
          </a>
        </li>
      </ul>
    </div>
		<footer class="footer">
			Lorem ipsum dolor sit amet consectetur.
		</footer>
  </div>
</template>
<style>
.header	{
	display: flex;
	align-items: center;
	/* justify-content: space-between; */
	height: 80px;
	padding: 0;
  margin: 0;
}
.primary {
	padding: 1rem;
  height: 1500px;
}
.secondary {
	padding: 1rem;
  height: 1500px;
}
.secondary-a {
	background: red;
}
.sidebar{
    background: rgb(5, 68, 104);
    /* position: fixed; */
    top: 0;
    left: 0;
    width: 225px;
    height: auto;
    padding: 20px 0;
    transition: all 0.5s ease;
}
.sidebar .profile {
    margin-bottom: 30px;
    text-align: center;
}
.sidebar .profile img{
    display: block;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin: 0 auto;
}
.sidebar .profile h3{
    color: #ffffff;
    margin: 10px 0 5px;
}
.sidebar .profile p{
    color: rgb(206, 240, 253);
    font-size: 14px;
}
@media (min-width: 1024px) {
	.page {
		display: grid;
		grid-template-columns: 20% 1fr 20%;
	}
	.header {
		grid-column: 1 / 4;
		grid-row: 1 / 2;
	}
	.primary {
		grid-column: 2 / 4;
		grid-row: 2 / 3;
	}
	.secondary-a {
		grid-column: 1 / 2;
		grid-row: 2 / 3;
	}
	.footer {
		grid-column: 1 / 4;
		grid-row: 3 / 4;
	}
}
@media (min-width: 1400px) {
	.page {
		grid-template-columns: 300px 1fr 300px;
	}
}
</style>