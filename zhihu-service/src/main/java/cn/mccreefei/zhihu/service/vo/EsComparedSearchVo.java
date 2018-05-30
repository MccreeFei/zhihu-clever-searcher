package cn.mccreefei.zhihu.service.vo;

import lombok.Data;

import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-01-29 10:17
 */
@Data
public class EsComparedSearchVo implements Comparable<EsComparedSearchVo> {
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
    //排序相关
    private Integer matchScore;
    private Integer userScore;
    private Integer textScore;
    private Integer comparedScore;

    @Override
    public int compareTo(EsComparedSearchVo vo) {
        return vo.getComparedScore() - comparedScore;
    }
}
