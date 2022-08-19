package com.ssafy.api.service;

import com.ssafy.api.request.CommunityRegistPostReq;
import com.ssafy.api.response.CommentRes;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;

import java.util.List;

public interface CommunityService {
    Community createArticle(User userInfo, CommunityRegistPostReq communityRegisterInfo);

    List<CommunityRes> getListByGenre(int genre, int category);

    List<CommunityRes> getListSearch(int genre, int category, String searchcategory, String searchword);

    List<CommunityRes> getAllList();

    Community getArticleByArticleId(long article_id);

    void update(long article_id, CommunityRegistPostReq Info);

    void deletearticle(long article_id);

    Comment createComment(User user, String comment, Community community) ;

    Comment updateComment(String comment, long comment_id);

    void deleteComment(long comment_id);

    Community recommend(long article_id);

    Community updateViewCnt(long article_id, Community community);

    List<CommunityRes> getListByUserId(String userId);

    List<CommentRes> getCommentListByUserId(String userId);

    List<CommunityRes> getPopularList();

    List<Comment> getCommentListByArticleId(long article_id);
}
