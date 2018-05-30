package cn.mccreefei.zhihu.service.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-01-23 9:30
 */
@Data
public class EsSearchVo {
    private Integer textType;
    //回答
    private Integer questionId;
    private Integer answerId;
    //文章
    private Integer articleId;
    //文本
    private String textUrl;
    private String textTitle;
    private Integer textAgrees;
    private Integer textComments;
    //作者
    private String characterUrl;
    private String headUrl;
    private String userName;
    private String simpleDescription;
    private Integer userThanks;
    private Integer userAgrees;
    private Integer userCollects;
    private Integer userFollowers;
    private Integer userFollowees;
    //es相关
    private Double searchScore;
    List<String> highLightTexts;
    private String searchText;

    public EsSearchVo(){}

    @Builder
    public EsSearchVo(Integer textType, Integer questionId, Integer answerId, Integer articleId) {
        this.textType = textType;
        this.questionId = questionId;
        this.answerId = answerId;
        this.articleId = articleId;
    }
}
