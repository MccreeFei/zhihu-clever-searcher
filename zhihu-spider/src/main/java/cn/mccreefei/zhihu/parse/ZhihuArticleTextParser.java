package cn.mccreefei.zhihu.parse;

import cn.mccreefei.zhihu.model.ZhihuArticleText;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.Date;

/**
 * @author MccreeFei
 * @create 2017-12-01 9:04
 */
@Slf4j
@Component
public class ZhihuArticleTextParser {
    public void parseArticleTextInfo(Page page) {
        String url = page.getRequest().getUrl();
        try {
            Integer articleId = getArticleId(url);

            String content = page.getHtml().xpath("//div[@id='react-root']//div[@class='RichText']").get();

            Date date = new Date();
            ZhihuArticleText articleText = new ZhihuArticleText.ArticleTextBuilder().setArticleId(articleId).setContent(content)
                    .setCreateTime(date).setModifyTime(date).build();
            page.putField("articleText", articleText);
        }catch (Exception e){
            log.error("parse article text failed!", e);
        }

    }

    private Integer getArticleId(String url) throws IndexOutOfBoundsException, NumberFormatException {
        int index = url.indexOf("/p/");
        return Integer.parseInt(url.substring(index + 3));
    }
}
