<template>
  <p>perform detail 화면입니다</p>
  <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-9">
                    <div class="page-title">
                        <h2 class="text-dark fw-light lh-base mt-5">레~~~~~~~~~~베카</h2>
                        <p class="fs-16 text-muted mb-0">여기에 뭐 넣을지 생각해봐야할듯</p>
                    </div>
                </div>
            </div>
            <!-- end row -->
        </div>
  </section>
    <!-- End Title -->

    <section class="sm-section">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-md-5 mt-4">
                            <div class="position-relative">
                                <img class="img-fluid rounded-3" src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg" alt="img" />
                            </div>
                            <div class="row">
                                <div class="col-sm-4 mt-4">
                                    <a href=""><img class="img-fluid rounded" src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg"
                                            alt="img" /></a>
                                </div>
                                <div class="col-sm-4 mt-4">
                                    <a href=""><img class="img-fluid rounded" src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg"
                                            alt="img" /></a>
                                </div>
                                <div class="col-sm-4 mt-4">
                                    <a href=""><img class="img-fluid rounded" src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg"
                                            alt="img" /></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-7 mt-4">
                            <div class="pro-detail-content">
                                <div class="price my-3">
                                    <ins class="pe-2 fs-18 text-success fw-semibold text-decoration-none">공연 기간</ins>
                                    <del class="text-muted fs-18">API에서 불러온 정보 넣기</del>
                                </div>

                                <p class="text-muted fs-16 my-3">
                                    강아지귀여워 강아지귀여워 강아지귀여워 강아지귀여워 강아지귀여워 강아지귀여워 강아지귀여워 강아지귀여워 강아지귀여워 강아지귀여워
                                </p>
                                <div class="rating my-3">
                                    <i class="mdi mdi-star fs-20 text-warning d-inline-block"></i>
                                    <h5 class="d-inline-block fs-16 mb-0">5.0</h5>
                                </div>

                                <div class="details mb-4">
                                    <ul class="list-unstyled">
                                        <li class="text-muted py-1"><i data-feather="arrow-right"
                                                class="icon-xxs me-2"></i> API에서 불러온 정보 넣기</li>
                                        <li class="text-muted py-1"><i data-feather="arrow-right"
                                                class="icon-xxs me-2"></i> API에서 불러온 정보 넣기</li>
                                        <li class="text-muted py-1"><i data-feather="arrow-right"
                                                class="icon-xxs me-2"></i> API에서 불러온 정보 넣기</li>
                                        <li class="text-muted py-1"><i data-feather="arrow-right"
                                                class="icon-xxs me-2"></i> API에서 불러온 정보 넣기</li>
                                    </ul>
                                    <ul class="list-unstyled list-inline"></ul>
                                </div>
                                <div class="qty mb-4">
                                    <h6 class="d-inline-block fw-semibold">Select Items :</h6>
                                    <select class="form-select border d-inline-block ms-4 w-25" id="autoSizingSelect">
                                        <option selected="">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                </div>
                                <div class="color mb-4">
                                    <h6 class="d-inline-block fw-semibold">Color :</h6>
                                    <ul class="d-inline-block list-unstyled list-inline align-middle ms-3 mb-0">
                                        <li class="list-inline-item me-0">
                                            <a href="javascript:void(0)"><i
                                                    class="mdi mdi-invert-colors fs-5 text-success"></i></a>
                                        </li>
                                        <li class="list-inline-item me-0">
                                            <a href="javascript:void(0)"><i
                                                    class="mdi mdi-invert-colors fs-5 text-primary"></i></a>
                                        </li>
                                        <li class="list-inline-item me-0">
                                            <a href="javascript:void(0)"><i
                                                    class="mdi mdi-invert-colors fs-5 text-danger"></i></a>
                                        </li>
                                        <li class="list-inline-item me-0">
                                            <a href="javascript:void(0)"><i
                                                    class="mdi mdi-invert-colors fs-5 text-warning"></i></a>
                                        </li>
                                        <li class="list-inline-item">
                                            <a href="javascript:void(0)"><i
                                                    class="mdi mdi-invert-colors fs-5 text-dark"></i></a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="hover-content">
                                    <a href="/shop/cart" class="btn btn-secondary my-2"> <i data-feather="heart"
                                            class="icon-xs me-2"></i> 목록보기 버튼으로 바꿔주세요</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- end pro-detail -->

    <!-- detail tab -->
    <section class="section bg-light">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <nav class="pro-detail-area">
                        <div class="nav nav-tabs border-bottom detail-title" id="nav-tab" role="tablist">
                            <a class="nav-link border-0 active" id="nav-home-tab" data-bs-toggle="tab" href="#nav-home"
                                role="tab" aria-controls="nav-home" aria-selected="true">소개</a>
                            <a class="nav-link border-0" id="nav-profile-tab" data-bs-toggle="tab" href="#nav-profile"
                                role="tab" aria-controls="nav-profile" aria-selected="false">공연장</a>
                        </div>
                    </nav>
                    <div class="tab-content py-4" id="nav-tabContent">
                        <div class="tab-pane fade show active" id="nav-home" role="tabpanel"
                            aria-labelledby="nav-home-tab">
                            <p class="text-muted fs-16">
                                API에서 불러온 소개사진 넣기!!
                            </p>

                            <div class="row text-muted">
                                <div class="col-md-6">
                                    <ul class="list-unstyled">
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <ul class="list-unstyled">
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                        <li class="py-1"><i class="mdi mdi-circle-medium text-primary"></i> API에서 불러온 정보 넣기</li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                            <h6 class="lh-base fw-medium">지도 API 넣기!!</h6>
                            <ul class="list-unstyled my-4">
                                <li class="list-inline d-flex py-3">
                                    <div>
                                        <a href="javascript:void(0)">
                                            <img class="rounded-circle img-thumbnail" src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg"
                                                alt="" />
                                        </a>
                                    </div>
                                    <div class="ps-4">
                                        <h6 class="d-inline-block fs-16 mb-0">John Barrows</h6>
                                        <div class="rating d-inline-block ps-sm-4">
                                            <i class="mdi mdi-star fs-16 text-warning d-inline-block"></i>
                                            <h5 class="d-inline-block fs-16">5.0</h5>
                                        </div>
                                        <p class="text-muted">24th January, 2021</p>
                                        <p class="text-muted fs-16">
                                            API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기
                                        </p>
                                    </div>
                                </li>
                                <li class="list-inline d-flex py-3">
                                    <div>
                                        <a href="javascript:void(0)">
                                            <img class="rounded-circle img-thumbnail" src="http://img.insight.co.kr/static/2018/07/26/700/5r387l930063zu4se350.jpg"
                                                alt="" />
                                        </a>
                                    </div>
                                    <div class="ps-4">
                                        <h6 class="d-inline-block fs-16 mb-0">James Marks</h6>
                                        <div class="rating d-inline-block ps-sm-4">
                                            <i class="mdi mdi-star fs-16 text-warning d-inline-block"></i>
                                            <h5 class="d-inline-block fs-16">4.9</h5>
                                        </div>
                                        <p class="text-muted">2nd January, 2021</p>
                                        <p class="text-muted fs-16">
                                            API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기 API에서 불러온 정보 넣기
                                        </p>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <!-- end nav -->

                </div>
            </div>
            <!-- end row -->
        </div>
    </section>
</template>

<script>
export default {

}
</script>

