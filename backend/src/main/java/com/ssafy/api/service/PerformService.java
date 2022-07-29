package com.ssafy.api.service;

import com.ssafy.api.response.PerformInfoRes;
import com.ssafy.api.response.PerformRes;

import java.util.List;

public interface PerformService {
    List<PerformRes> getPerformAllList();

    PerformInfoRes getPerformInfo(String mt20id);
}
