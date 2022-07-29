<template>
  <div>
    <section>
      <div class="content">
        <div class="card">
          <div class="card-body">
            <h1>멍뭉이's Profile</h1>
            <p>
              <button class="btn">
              팔로우버튼 넣어주기
              </button>
            </p>

            <h5 type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#exampleModal">
              팔로워
            </h5>
            : 3  /  
            <div focus="false" class="modal fade" id="exampleModal" aria-labelledby="exampleModalLabel" aria-hidden="flase">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">멍뭉이를 팔로우한 목록</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div data-bs-dismiss="modal">
                      <ul>
                        <li>
                          <a href="">
                            팔로우한사람이름
                          </a>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>



            <h5 type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#exampleModal1">
              팔로잉
            </h5>
            : 2
            <div focus="false" class="modal fade" id="exampleModal1" aria-labelledby="exampleModalLabel1" aria-hidden="flase">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel1">멍뭉이가 팔로잉한 목록</h5>
                    <button type="button2" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                    <div data-bs-dismiss="modal">
                      <ul>
                        <li>
                          <a href="">
                            팔로잉한사람이름
                          </a>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <div class="modal-footer">
                    <button type="button2" class="btn btn-outline-secondary" data-bs-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>
            <div>
              <img src="@/assets/account/setting.png" alt="" style="width">
            </div>

          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'ProfileItem',
  data() {
    return {
      input: {
        profileImg: ''
      }
    }
  }

}
</script>

<style>

</style>