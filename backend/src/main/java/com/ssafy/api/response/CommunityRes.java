package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("CommunityListRes")
public class CommunityRes {
    //-------글 목록 항목
    //글 제목
    String article_title;
    //글 작성자
    String user_id;
    //조회수
    int view_cnt;
    //추천수
    int recommend;
    //댓글 수
    long comment_cnt;

    public static CommunityRes of(String article_title,
                                  String user_id,
                                  int view_cnt,
                                  int recommend,
                                  long comment_cnt
    ) {
        CommunityRes res = new CommunityRes();
        //------글 목록 항목
        res.setArticle_title(article_title);
        res.setUser_id(user_id);
        res.setView_cnt(view_cnt);
        res.setRecommend(recommend);
        res.setComment_cnt(comment_cnt);
        return res;
    }

    //-------글 상세보기 항목
    //글 내용
    String article_content;
    //글 작성시간
    LocalDateTime regtime;
    //------글 상세보기 항목
    public static CommunityRes of(String article_title,
                                  String user_id,
                                  int view_cnt,
                                  int recommend,
                                  String article_content,
                                  LocalDateTime regtime) {
        CommunityRes res = new CommunityRes();
        //------글 상세정보 항목
        res.setArticle_title(article_title);
        res.setUser_id(user_id);
        res.setView_cnt(view_cnt);
        res.setRecommend(recommend);
        res.setArticle_content(article_content);
        res.setRegtime(regtime);
        return res;
    }
}
