package cn.mccreefei.zhihu.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2017-11-30 14:56
 */
@Data
public class ZhihuAnswerText {
    private Integer id;
    private Integer questionId;
    private Integer answerId;
    private String content;
    private Date createTime;
    private Date modifyTime;

    public static class AnswerTextBuilder{
        private Integer questionId;
        private Integer answerId;
        private String content;
        private Date createTime;
        private Date modifyTime;

        public AnswerTextBuilder setQuestionId(Integer questionId) {
            this.questionId = questionId;
            return this;
        }

        public AnswerTextBuilder setAnswerId(Integer answerId) {
            this.answerId = answerId;
            return this;
        }

        public AnswerTextBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public AnswerTextBuilder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public AnswerTextBuilder setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public ZhihuAnswerText build(){
            ZhihuAnswerText answerText = new ZhihuAnswerText();
            answerText.setQuestionId(questionId);
            answerText.setAnswerId(answerId);
            answerText.setContent(content);
            answerText.setCreateTime(createTime);
            answerText.setModifyTime(modifyTime);
            return answerText;
        }
    }
}
