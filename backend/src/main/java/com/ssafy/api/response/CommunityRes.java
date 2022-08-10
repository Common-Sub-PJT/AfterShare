package com.ssafy.api.response;

import com.ssafy.db.entity.Community;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("CommunityListRes")
public class CommunityRes {
    //-------글 목록 항목
    //글 번호
    long _id;
    //글 제목
    String _title;
    //글 작성자
    String user_id;
    //조회수
    int view_cnt;
    //추천수
    int recommend;
    //댓글 수
    long comment_cnt;

    public static CommunityRes of(Community community,
                                  long cnt) {
        CommunityRes res = new CommunityRes();
        //------글 목록 항목
        res.set_id(community.getArticle_id());
        res.set_title(community.getArticle_title());
        res.setUser_id(community.getUser().getUserId());
        res.setView_cnt(community.getView_cnt());
        res.setRecommend(community.getRecommend());
        res.setComment_cnt(cnt);
        return res;
    }

    //-------글 상세보기 항목
    //글 내용
    String _content;
    //글 작성시간
    LocalDateTime regtime;
    //------글 상세보기 항목
    public static CommunityRes of(String _title,
                                  String user_id,
                                  int view_cnt,
                                  int recommend,
                                  String _content,
                                  LocalDateTime regtime) {
        CommunityRes res = new CommunityRes();
        //------글 상세정보 항목
        res.set_title(_title);
        res.setUser_id(user_id);
        res.setView_cnt(view_cnt);
        res.setRecommend(recommend);
        res.set_content(_content);
        res.setRegtime(regtime);
        return res;
    }
}
