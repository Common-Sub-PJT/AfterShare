package com.ssafy.api.service;

import com.ssafy.api.request.CommunityRegistPostReq;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;

import java.util.List;

public interface CommunityService {
    Community createArticle(User userInfo, CommunityRegistPostReq communityRegisterInfo);

    List<CommunityRes> getArticleListByGenre(int genre, int category);

    List<CommunityRes> getAllArticleList();

    Community getArticleByArticleId(long article_id);

    void updateArticle(long article_id, CommunityRegistPostReq articleInfo);

    void deleteAlticle(long article_id);

    Comment createComment(User user, String comment, Community community) ;

    Comment updateComment(String comment, long comment_id);

    void deleteComment(long comment_id);

    Community recommendArticle(long article_id);

    Community updateViewCnt(long article_id, Community community);
}
