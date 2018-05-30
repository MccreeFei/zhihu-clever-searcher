package cn.mccreefei.zhihu.magic;

import cn.mccreefei.zhihu.parse.ZhihuAnswerParser;
import cn.mccreefei.zhihu.parse.ZhihuArticleParser;
import cn.mccreefei.zhihu.parse.ZhihuUserParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-15 13:38
 */
@Slf4j
@Component
public class ZhihuPageProcessor implements PageProcessor {
    private ZhihuUserParser userParser;
    private ZhihuAnswerParser answerParser;
    private ZhihuArticleParser articleParser;
    private static final ResourceBundle resource = ResourceBundle.getBundle("zhihu");
    private static final String FOLLOW_PAGE_REGEX = "(.*/following)|(.*/following\\?page=\\d)";
    private static final String ANSWER_PAGE_REGEX = "(.*/answers/by_votes)|(.*/answers/by_votes\\?page=\\d)";
    private static final String ARTICLE_PAGE_REGEX = "(.*/posts/posts_by_votes)|(.*/posts/posts_by_votes\\?page=\\d)";
    private Site site;

    public ZhihuPageProcessor() {
        int retryTimes = 0;
        int crawlSleepTime = 0;
        try {
            retryTimes = Integer.parseInt(resource.getString("RETRY_TIMES"));
            crawlSleepTime = Integer.parseInt(resource.getString("CRAWL_SLEEP_TIME"));
        } catch (Exception e) {
            log.warn("get RETRY_TIMES or CRAWL_SLEEP_TIME from resource failed! use default value 3 for RETRY_TIME and 2000 for CRAWL_SLEEP_TIME");
            retryTimes = 3;
            crawlSleepTime = 2000;
        }
        site = Site.me().setRetryTimes(retryTimes).setSleepTime(crawlSleepTime).setCharset("utf-8").setTimeOut(10000).
                setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36");

    }

    @Autowired
    public void setUserParser(ZhihuUserParser userParser) {
        this.userParser = userParser;
    }

    @Autowired
    public void setAnswerParser(ZhihuAnswerParser answerParser) {
        this.answerParser = answerParser;
    }

    @Autowired
    public void setArticleParser(ZhihuArticleParser articleParser) {
        this.articleParser = articleParser;
    }

    @Override
    public void process(Page page) {
        addTargetUrls(page);
        String url = page.getRequest().getUrl();
        if (url.endsWith("/following")) {
            userParser.parseUserInfo(page);
        } else if (url.matches(ANSWER_PAGE_REGEX)) {
            answerParser.parseAnswerInfo(page);
        } else if (url.matches(ARTICLE_PAGE_REGEX)) {
            articleParser.parseArticleInfo(page);
        } else {
            page.setSkip(true);
        }
    }

    protected void addTargetUrls(Page page) {
        String sourceUrl = page.getRequest().getUrl();
        try {
            if (sourceUrl.matches(FOLLOW_PAGE_REGEX)) {
                //以/following结尾的url将分页url添加进队列
//                if (sourceUrl.endsWith("/following")) {
//                    List<String> pageList = page.getHtml().xpath("//div[@class='Profile-main']//div[@class='Pagination']" +
//                            "/button[@class='Button PaginationButton Button--plain']/text()").all();
//
//                    if (pageList != null && pageList.size() > 0) {
//                        int maxPage = Integer.parseInt(pageList.get(pageList.size() - 1));
//                        for (int i = 2; i <= maxPage; i++) {
//                            String pageUrl = sourceUrl + "?page=" + i;
//                            page.addTargetRequest(new Request(pageUrl).setPriority(20));
//                        }
//                    }
//                }
                List<String> urlList = page.getHtml().xpath("//div[@id='Profile-following']//div[@class='List-item]" +
                        "//div[@class='ContentItem-head']//a[@class='UserLink-link]").links().all();
                if (urlList != null && urlList.size() > 0) {
                    for (String url : urlList) {
                        page.addTargetRequest(new Request(url + "/following").setPriority(100));
                        page.addTargetRequest(new Request(url + "/answers/by_votes").setPriority(100));
                        page.addTargetRequest(new Request(url + "/posts/posts_by_votes").setPriority(100));
                    }
                }
            }
        } catch (Exception e) {
            log.error("add target urls failed!", e);
        }
    }


    @Override
    public Site getSite() {
        return site;
    }

}
