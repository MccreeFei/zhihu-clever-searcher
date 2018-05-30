package cn.mccreefei.zhihu.dao.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author MccreeFei
 * @create 2018-01-23 10:40
 */
@Data
public class AnswerWithUserDo {
    private Integer questionId;
    private Integer answerId;
    private String answerUrl;
    private String questionTitle;
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

    public AnswerWithUserDo(){}
    @Builder
    public AnswerWithUserDo(Integer questionId, Integer answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }
}
