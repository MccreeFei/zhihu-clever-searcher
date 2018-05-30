package cn.mccreefei.zhihu.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2017-11-10 10:26
 */
@Data
public class ZhihuAnswer {
    private Integer id;
    private String characterUrl;
    private String answerUrl;
    private Integer questionId;
    private String questionTitle;
    private Integer answerId;
    private Integer agrees;
    private Integer comments;
    private Date createTime;
    private Date modifyTime;

    public static class AnswerBuilder {
        private String characterUrl;
        private String answerUrl;
        private Integer questionId;
        private String questionTitle;
        private Integer answerId;
        private Integer agrees;
        private Integer comments;
        private Date createTime;
        private Date modifyTime;

        public AnswerBuilder setCharacterUrl(String characterUrl) {
            this.characterUrl = characterUrl;
            return this;
        }

        public AnswerBuilder setAnswerUrl(String answerUrl) {
            this.answerUrl = answerUrl;
            return this;
        }

        public AnswerBuilder setQuestionId(Integer questionId) {
            this.questionId = questionId;
            return this;
        }

        public AnswerBuilder setQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
            return this;
        }

        public AnswerBuilder setAnswerId(Integer answerId) {
            this.answerId = answerId;
            return this;
        }

        public AnswerBuilder setAgrees(Integer agrees) {
            this.agrees = agrees;
            return this;
        }

        public AnswerBuilder setComments(Integer comments) {
            this.comments = comments;
            return this;
        }

        public AnswerBuilder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public AnswerBuilder setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public ZhihuAnswer build() {
            ZhihuAnswer result = new ZhihuAnswer();
            result.setCharacterUrl(characterUrl);
            result.setAnswerUrl(answerUrl);
            result.setQuestionId(questionId);
            result.setQuestionTitle(questionTitle);
            result.setAnswerId(answerId);
            result.setAgrees(agrees);
            result.setComments(comments);
            result.setCreateTime(createTime);
            result.setModifyTime(modifyTime);
            return result;
        }
    }

}
