package cn.mccreefei.zhihu;

import cn.mccreefei.zhihu.dao.ZhihuAnswerDao;
import cn.mccreefei.zhihu.dao.ZhihuArticleDao;
import cn.mccreefei.zhihu.magic.ZhihuPipeline;
import cn.mccreefei.zhihu.magic.ZhihuTextPageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-12-01 9:10
 */
@Slf4j
@Component
public class ZhihuTextSpider {
    private ZhihuTextPageProcessor textPageProcessor;
    private ZhihuPipeline pipeline;
    private ZhihuAnswerDao answerDao;
    private ZhihuArticleDao articleDao;
    private final static ResourceBundle resource = ResourceBundle.getBundle("zhihu");

    @Autowired
    public void setTextPageProcessor(ZhihuTextPageProcessor textPageProcessor) {
        this.textPageProcessor = textPageProcessor;
    }

    @Autowired
    public void setPipeline(ZhihuPipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Autowired
    public void setAnswerDao(ZhihuAnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Autowired
    public void setArticleDao(ZhihuArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void crawl(int threadNum) {
        List<String> answerUrlList = answerDao.getZhihuAnswerUrlList();
        List<String> sub = answerUrlList.subList(0, 2600);
        answerUrlList.removeAll(sub);
        List<String> articleUrlList = articleDao.getZhihuArticleUrlList();
        Spider spider = Spider.create(textPageProcessor).addPipeline(pipeline)
                .addUrl(answerUrlList.toArray(new String[answerUrlList.size()]))
                .addUrl(articleUrlList.toArray(new String[articleUrlList.size()]));
        spider.run();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-dao.xml");
        ZhihuTextSpider spider = context.getBean(ZhihuTextSpider.class);
        spider.crawl(1);

    }
}
