package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.CommunityRes;
import com.ssafy.db.entity.Community;
import com.ssafy.db.entity.QCommunity;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 커뮤니티 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class CommunityRepositorySupport{
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QCommunity qCommunity = QCommunity.community;

    public List<Community> findCommunityListByGenre(int genre, int category) {
        List<Community> communities = jpaQueryFactory.select(qCommunity).from(qCommunity)
                    .where(qCommunity.article_genre.eq(genre),qCommunity.article_category.eq(category)).orderBy(qCommunity.recommend.desc()).fetch();
        if(communities==null) return null;
        return communities;
    }

    public List<Community> findAllCommunityList() {
        List<Community> communities = jpaQueryFactory.select(qCommunity).from(qCommunity).orderBy(qCommunity.recommend.desc()).fetch();
        if(communities == null) return null;
        return communities;
    }

    public Community findArticleByArticleId(long article_id) {
        Community res = jpaQueryFactory.select(qCommunity).from(qCommunity)
                .where(qCommunity.article_id.eq(article_id)).fetchOne();
        if(res==null) return null;
        return res;
    }

    public List<Community> findCommunityListByUserId(String userId) {
        List<Community> communities = jpaQueryFactory.select(qCommunity).from(qCommunity)
                .where(qCommunity.user.userId.eq(userId)).orderBy(qCommunity.recommend.desc()).fetch();
        if(communities==null) return null;
        return communities;
    }

    public List<Community> findArticleByRecommend() {
        List<Community> communities = jpaQueryFactory.select(qCommunity).from(qCommunity).orderBy(qCommunity.recommend.desc()).limit(5).fetch();
        if(communities==null) return null;
        return communities;
    }

    public List<Community> findCommunityListSearch(int genre, int category, String searchcategory, String searchword) {
        List<Community> communities = null;
        if(searchcategory.equals("제목")){
            communities = jpaQueryFactory.select(qCommunity).from(qCommunity)
                    .where(qCommunity.article_genre.eq(genre),
                            qCommunity.article_category.eq(category),
                            qCommunity.article_title.like("%" + searchword + "%") )
                    .orderBy(qCommunity.recommend.desc()).fetch();
        }else if(searchcategory.equals("작성자")){
            communities = jpaQueryFactory.select(qCommunity).from(qCommunity)
                    .where(qCommunity.article_genre.eq(genre),
                            qCommunity.article_category.eq(category),
                            qCommunity.user.userId.like("%" + searchword + "%"))
                    .orderBy(qCommunity.recommend.desc()).fetch();
        }
        if(communities==null) return null;
        return communities;
    }
}
