package com.ssafy.api.service;

import com.ssafy.api.request.CommunityRegistPostReq;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;

import java.util.List;

public interface CommunityService {
    Community create(User userInfo, CommunityRegistPostReq communityRegisterInfo);

    List<CommunityRes> getListByGenre(int genre, int category);

    List<CommunityRes> getListSearch(int genre, int category, String searchcategory, String searchword);

    List<CommunityRes> getAllList();

    Community getById(long _id);

    void update(long _id, CommunityRegistPostReq Info);

    void deletearticle(long _id);

    Comment createComment(User user, String comment, Community community) ;

    Comment updateComment(String comment, long comment_id);

    void deleteComment(long comment_id);

    Community recommend(long _id);

    Community updateViewCnt(long _id, Community community);

    List<CommunityRes> getListByUserId(String userId);

    List<String> getCommentListByUserId(String userId);

    List<CommunityRes> getPopularList();
}
