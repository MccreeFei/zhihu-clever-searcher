package cn.mccreefei.zhihu.dao.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-01-23 10:43
 */
@Data
public class ArticleWithUserDo {
    private Integer articleId;
    private String articleUrl;
    private String articleTitle;
    private Integer textAgrees;
    private Integer textComments;

    private String characterUrl;
    private String headUrl;
    private String userName;
    private String simpleDescription;
    private Integer userThanks;
    private Integer userAgrees;
    private Integer userCollects;
    private Integer userFollowers;
    private Integer userFollowees;

    @Builder
    public ArticleWithUserDo(Integer articleId) {
        this.articleId = articleId;
    }

    public ArticleWithUserDo(){}
}
