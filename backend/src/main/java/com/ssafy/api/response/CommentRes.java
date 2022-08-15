package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("CommentRes")
public class CommentRes {
    //게시글 번호
    long article_id;
    //댓글 내용
    String comment_content;

    public static CommentRes of(long article_id, String comment_content) {
        CommentRes res = new CommentRes();

        res.setArticle_id(article_id);
        res.setComment_content(comment_content);

        return res;
    }
}
