<template>
  <div class="page">
    <header class="header">
      <div class="logo">
        <!-- <img src="" alt="img"> -->
        <h1>뒤풀이어</h1>
      </div>
      <div class="searchbar">
      	<form class="search-form">
				  <input type="search">
				  <input type="submit" value="찾기">
			  </form>
      </div>
    </header>
    <section class="primary"><RouterView /></section>
    <div class="wrapper">
      <div class="sidebar secondary secondary-a">
        <!-- profile -->
        <div class="profile">
            <img src="https://1.bp.blogspot.com/-vhmWFWO2r8U/YLjr2A57toI/AAAAAAAACO4/0GBonlEZPmAiQW4uvkCTm5LvlJVd_-l_wCNcBGAsYHQ/s16000/team-1-2.jpg" alt="프사">
            <h3>닉네임</h3>
            <RouterLink to="/mypage/:username" class="text-decoration-none">
                <p>마이페이지</p>
            </RouterLink>
        </div>
        <ul>
          <li>
            <div>
              <RouterLink to="/">
                <span class="icon"><i class="fas fa-home"></i></span>
                <span class="item text-decoration-none">Home</span>
              </RouterLink>
            </div>
          </li>
          <li>
            <div>
              <RouterLink to="/login">
                <span class="icon"><i class="fas fa-desktop"></i></span>
                <span class="item text-decoration-none">Login</span>
              </RouterLink>
            </div>
          </li>
          <li>
            <div>
              <RouterLink to="/signup">
                <span class="icon"><i class="fas fa-user-friends"></i></span>
                <span class="item text-decoration-none">Signup</span>  
              </RouterLink>
            </div>
          </li>
          <li>
            <div>
              <RouterLink to="/conferencelist">
                <span class="icon"><i class="fas fa-user-friends"></i></span>
                <span class="item text-decoration-none">뒷풀이방</span>
              </RouterLink>
            </div>
          </li>
          <li>
            <div>
              <RouterLink to="/history/:userId">
                <span class="icon"><i class="fas fa-tachometer-alt"></i></span>
                <span class="item text-decoration-none">history</span>
              </RouterLink>
            </div>
          </li>
          <li>
            <div>
              <RouterLink to="/perform/:performId">
                <span class="icon"><i class="fas fa-chart-line"></i></span>
                <span class="item text-decoration-none">Perform</span>
              </RouterLink>
            </div>
          </li>
          <!-- <li>
            <a href="#">
              <span class="icon"><i class="fas fa-user-shield"></i></span>
              <span class="item">Admin</span>
            </a>
          </li> -->
        </ul>
      </div>
    </div>
		<footer class="footer">
			<p>B303 공통프로젝트 뒤풀이어</p>
		</footer>
  </div>
</template>
<script>

</script>
<style lang="scss" scoped>
.footer {
  text-align: center;
}
.searchbar {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.header	{
  position: fixed;
  width: 100%;
	display: flex;
	align-items: center;
	justify-content: space-between;
	height: 80px;
	padding: 0;
  margin: 0;
  background-color: #fafafa;
}
header .header{
  position: fixed;
}
.primary {
	padding: 1rem;
  height: 1500px;
  top: 80px;
  margin-top: 80px;
}
.secondary {
	padding: 1rem;
  height: 1500px;
}
.sidebar{
    background: #fafafa;
    position: fixed;
    top: 80px;
    left: 0;
    width: 225px;
    height: 100%;
    padding: 20px 0;
    transition: all 0.5s ease;
    // text-align: center;
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
    color: #605b5b;
    margin: 10px 0 5px;
}
.sidebar .profile p{
    color: rgb(206, 240, 253);
    font-size: 14px;
}
.wrapper .sidebar ul li{
  list-style:none;
  padding-left:0px;
}
.wrapper .sidebar ul li div{
  display: block;
  padding: 0px;
  border-bottom: 1px solid #10558d;
  color: rgb(241, 237, 237);
  font-size: 16px;
  position: relative;
}
.wrapper .sidebar ul li a .icon{
    color: #dee4ec;
    width: 30px;
    display: inline-block;
}
.wrapper .sidebar ul li a:hover,
.wrapper .sidebar ul li a.active{
    color: #0c7db1;

    background:white;
    border-right: 2px solid rgb(5, 68, 104);
}

.wrapper .sidebar ul li a:hover .icon,
.wrapper .sidebar ul li a.active .icon{
    color: #4e4c67;
}

.wrapper .sidebar ul li a:hover:before,
.wrapper .sidebar ul li a.active:before{
    display: block;
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
@media (min-width: 800px) {
	.page {
		grid-template-columns: 300px 1fr 300px;
	}
}

</style>