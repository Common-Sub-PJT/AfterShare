package com.ssafy.api.service;

import com.ssafy.api.response.PerformInfoRes;
import com.ssafy.api.response.PerformRes;

import java.util.List;

public interface PerformService {
    //--------조다연 시작--------
    List<PerformRes> getPerformAllList();

    PerformInfoRes getPerformInfo(String mt20id);
    //--------조다연 끝--------
}
