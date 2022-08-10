package com.ssafy.api.service;

import com.ssafy.api.request.CommunityRegistPostReq;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.db.entity.Comment;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.CommentRepository;
import com.ssafy.db.repository.CommentRepositorySupport;
import com.ssafy.db.repository.CommunityRepository;
import com.ssafy.db.repository.CommunityRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *	커뮤니티 API 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("CommunityService")
public class CommunityServiceImpl implements CommunityService{
    //차송희 커뮤니티 시작-------------------------------------------
    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    CommunityRepositorySupport communityRepositorySupport;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentRepositorySupport commentRepositorySupport;


    @Override
    public Community createArticle(User userInfo, CommunityRegistPostReq communityRegisterInfo) {
        Community community = new Community();
        community.setArticle_genre(communityRegisterInfo.getAlticle_genre());
        community.setArticle_category(communityRegisterInfo.getAlticle_category());
        community.setArticle_title(communityRegisterInfo.getAlticle_title());
        community.setArticle_content(communityRegisterInfo.getAlticle_content());
        community.setUser(userInfo);
        return communityRepository.save(community);
    }
    //장르별 글 목록 가져오기
    @Override
    public List<CommunityRes> getArticleListByGenre(int genre, int category) {
        List<Community> communityList = communityRepositorySupport.findCommunityListByGenre(genre, category);
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu : communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentByArticleId(commu.getArticle_id())));
        }
        return res;
    }

    @Override
    public List<CommunityRes> getArticleListSearch(int genre, int category, String searchcategory, String searchword) {
        List<Community> communityList = communityRepositorySupport.findCommunityListSearch(genre, category, searchcategory, searchword);
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu : communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentByArticleId(commu.getArticle_id())));
        }
        return res;
    }


    //전체 글 목록 가져오기
    @Override
    public List<CommunityRes> getAllArticleList() {
        List<Community> communityList = communityRepositorySupport.findAllCommunityList();
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu: communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentByArticleId(commu.getArticle_id())));
        }
        return res;
    }

    @Override
    public Community getArticleByArticleId(long article_id) {
        Community res = communityRepositorySupport.findArticleByArticleId(article_id);
        return res;
    }

    @Override
    public void updateArticle(long article_id, CommunityRegistPostReq articleInfo) {
        Community updateArticle = communityRepositorySupport.findArticleByArticleId(article_id);
        updateArticle.setArticle_title(articleInfo.getAlticle_title());
        updateArticle.setArticle_content(articleInfo.getAlticle_content());
        communityRepository.save(updateArticle);
    }

    @Override
    public void deleteAlticle(long article_id) {
        Community deleteArticle = communityRepositorySupport.findArticleByArticleId(article_id);
        communityRepository.deleteById(deleteArticle.getArticle_id());
    }
    //----------------------댓글 CRUD-------------------------------------------------------
    @Override
    public Comment createComment(User user, String comment, Community community) {
        Comment comments = new Comment();
        comments.setComment_content(comment);
        comments.setUser(user);
        comments.setCommunity(community);
        return commentRepository.save(comments);
    }

    @Override
    public Comment updateComment(String comment, long comment_id) {
        Comment updateComment = commentRepositorySupport.findCommentByCommentId(comment_id);
        updateComment.setComment_content(comment);
        return commentRepository.save(updateComment);
    }

    @Override
    public void deleteComment(long comment_id) {
        Comment deleteComment = commentRepositorySupport.findCommentByCommentId(comment_id);
        commentRepository.deleteById(comment_id);
    }
    @Override
    @Transactional
    public Community recommendArticle(long article_id) {
        Community recommendCommunity = communityRepositorySupport.findArticleByArticleId(article_id);
        int currentRecommend = recommendCommunity.getRecommend();
        recommendCommunity.setRecommend(currentRecommend+1);
        return communityRepository.save(recommendCommunity);
    }

    @Override
    @Transactional
    public Community updateViewCnt(long article_id, Community community){
        Community updateCommunity = communityRepositorySupport.findArticleByArticleId(article_id);
        int currnetViewCnt = updateCommunity.getView_cnt();
        updateCommunity.setView_cnt(currnetViewCnt+1);
        return communityRepository.save(updateCommunity);
    }

    @Override
    public List<CommunityRes> getArticleListByUserId(String userId) {
        List<Community> communityList = communityRepositorySupport.findCommunityListByUserId(userId);
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu: communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentByArticleId(commu.getArticle_id())));
        }
        return res;
    }

    @Override
    public List<String> getCommentListByUserId(String userId) {
        List<Comment> commentList = commentRepositorySupport.findCommentListByUserId(userId);
        List<String> res = new ArrayList<>();
        for(Comment comment: commentList){
            res.add(comment.getComment_content());
        }
        return res;
    }

    @Override
    public List<CommunityRes> getPopularArticleList() {
        List<Community> communityList = communityRepositorySupport.findArticleByRecommend();
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu: communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentByArticleId(commu.getArticle_id())));
        }
        return res;
    }
    //차송희 커뮤니티 끝-------------------------------------------
}
