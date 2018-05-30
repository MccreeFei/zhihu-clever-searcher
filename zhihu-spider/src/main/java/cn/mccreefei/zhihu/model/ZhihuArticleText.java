package cn.mccreefei.zhihu.model;

import lombok.Data;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2017-11-30 15:02
 */
@Data
public class ZhihuArticleText {
    private Integer id;
    private Integer articleId;
    private String content;
    private Date createTime;
    private Date modifyTime;

    public static class ArticleTextBuilder{
        private Integer articleId;
        private String content;
        private Date createTime;
        private Date modifyTime;

        public ArticleTextBuilder setArticleId(Integer articleId) {
            this.articleId = articleId;
            return this;
        }

        public ArticleTextBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public ArticleTextBuilder setCreateTime(Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public ArticleTextBuilder setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public ZhihuArticleText build(){
            ZhihuArticleText articleText = new ZhihuArticleText();
            articleText.setArticleId(articleId);
            articleText.setContent(content);
            articleText.setCreateTime(createTime);
            articleText.setModifyTime(modifyTime);
            return articleText;
        }
    }
}
