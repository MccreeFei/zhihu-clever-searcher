package cn.mccreefei.zhihu.magic;

import cn.mccreefei.zhihu.parse.ZhihuAnswerTextParser;
import cn.mccreefei.zhihu.parse.ZhihuArticleTextParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-30 13:23
 */
@Slf4j
@Component
public class ZhihuTextPageProcessor implements PageProcessor {
    private Site site;
    private ZhihuAnswerTextParser answerTextParser;
    private ZhihuArticleTextParser articleTextParser;
    private static final ResourceBundle resource = ResourceBundle.getBundle("zhihu");
    public ZhihuTextPageProcessor(){
        int retryTimes = 0;
        int crawlSleepTime = 0;
        try {
            retryTimes = Integer.parseInt(resource.getString("RETRY_TIMES"));
            crawlSleepTime = Integer.parseInt(resource.getString("TEXT_CRAWL_SLEEP_TIME"));
        } catch (Exception e) {
            log.warn("get RETRY_TIMES or CRAWL_SLEEP_TIME from resource failed! use default value 3 for RETRY_TIME and 2000 for CRAWL_SLEEP_TIME");
            retryTimes = 3;
            crawlSleepTime = 4000;
        }
        site = Site.me().setRetryTimes(retryTimes).setSleepTime(crawlSleepTime).setCharset("utf-8").setTimeOut(10000).
                setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36");

    }

    @Autowired
    public void setAnswerTextParser(ZhihuAnswerTextParser answerTextParser) {
        this.answerTextParser = answerTextParser;
    }

    @Autowired
    public void setArticleTextParser(ZhihuArticleTextParser articleTextParser) {
        this.articleTextParser = articleTextParser;
    }

    @Override
    public void process(Page page) {
        String url = page.getRequest().getUrl();
        if (isArticleUrl(url)){
            articleTextParser.parseArticleTextInfo(page);
        }else if (isAnswerUrl(url)){
            answerTextParser.parseAnswerTextInfo(page);
        }
    }

    private boolean isArticleUrl(String url){
        if (url != null && url.matches("https://zhuanlan.zhihu.com/p/\\d+")){
            return true;
        }
        return false;
    }

    private boolean isAnswerUrl(String url){
        if (url != null && url.matches("https://www.zhihu.com/question/\\d+/answer/\\d+")){
            return true;
        }
        return false;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
