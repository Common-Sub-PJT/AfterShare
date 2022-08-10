package com.ssafy.api.service;

import com.ssafy.api.request.CommunityRegistPostReq;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.api.response.FollowingRes;
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

import static com.ssafy.db.entity.QCommunity.community;

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
    public Community create(User userInfo, CommunityRegistPostReq communityRegisterInfo) {
        Community community = new Community();
        community.setArticle_genre(communityRegisterInfo.getArticle_genre());
        community.setArticle_category(communityRegisterInfo.getArticle_category());
        community.setArticle_title(communityRegisterInfo.getArticle_title());
        community.setArticle_content(communityRegisterInfo.getArticle_content());
        community.setUser(userInfo);
        return communityRepository.save(community);
    }
    //장르별 글 목록 가져오기
    @Override
    public List<CommunityRes> getListByGenre(int genre, int category) {
        List<Community> communityList = communityRepositorySupport.findCommunityListByGenre(genre, category);
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu : communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentById(commu.getArticle_id())));
        }
        return res;
    }

    @Override
    public List<CommunityRes> getListSearch(int genre, int category, String searchcategory, String searchword) {
        List<Community> communityList = communityRepositorySupport.findCommunityListSearch(genre, category, searchcategory, searchword);
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu : communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentById(commu.getArticle_id())));
        }
        return res;
    }


    //전체 글 목록 가져오기
    @Override
    public List<CommunityRes> getAllList() {
        List<Community> communityList = communityRepositorySupport.findAllCommunityList();
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu: communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentById(commu.getArticle_id())));
        }
        return res;
    }

    @Override
    public Community getById(long article_id) {
        Community res = communityRepositorySupport.findById(article_id);
        return res;
    }

    @Override
    public void update(long article_id, CommunityRegistPostReq Info) {
        Community update = communityRepositorySupport.findById(article_id);
        update.setArticle_title(Info.getArticle_title());
        update.setArticle_content(Info.getArticle_content());
        communityRepository.save(update);
    }

    @Override
    public void deletearticle(long article_id) {
        Community delete = communityRepositorySupport.findById(article_id);
        communityRepository.deleteById(delete.getArticle_id());
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
    public Community recommend(long article_id) {
        Community recommendCommunity = communityRepositorySupport.findById(article_id);
        int currentRecommend = recommendCommunity.getRecommend();
        recommendCommunity.setRecommend(currentRecommend+1);
        return communityRepository.save(recommendCommunity);
    }

    @Override
    @Transactional
    public Community updateViewCnt(long article_id, Community community){
        Community updateCommunity = communityRepositorySupport.findById(article_id);
        int currnetViewCnt = updateCommunity.getView_cnt();
        updateCommunity.setView_cnt(currnetViewCnt+1);
        return communityRepository.save(updateCommunity);
    }

    @Override
    public List<CommunityRes> getListByUserId(String userId) {
        List<Community> communityList = communityRepositorySupport.findCommunityListByUserId(userId);
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu: communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentById(commu.getArticle_id())));
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
    public List<CommunityRes> getPopularList() {
        List<Community> communityList = communityRepositorySupport.findByRecommend();
        List<CommunityRes> res = new ArrayList<>();
        for(Community commu: communityList){
            res.add(CommunityRes.of(commu,
                    commentRepositorySupport.countCommentById(commu.getArticle_id())));
        }
        return res;
    }
    //차송희 커뮤니티 끝-------------------------------------------
}
