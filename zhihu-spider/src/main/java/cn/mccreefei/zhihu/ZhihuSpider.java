package cn.mccreefei.zhihu;

import cn.mccreefei.zhihu.magic.SimpleSeleniumDownloader;
import cn.mccreefei.zhihu.magic.ZhihuPageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import javax.annotation.Resource;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author MccreeFei
 * @create 2017-11-15 14:06
 */
@Slf4j
@Component
public class ZhihuSpider {
    private ZhihuPageProcessor zhihuPageProcessor;
    private SimpleSeleniumDownloader simpleSeleniumDownloader;
    private Pipeline pipeline;
    private static final ResourceBundle resource = ResourceBundle.getBundle("zhihu");

    @Autowired
    public void setZhihuPageProcessor(ZhihuPageProcessor zhihuPageProcessor) {
        this.zhihuPageProcessor = zhihuPageProcessor;
    }

    @Autowired
    public void setSimpleSeleniumDownloader(SimpleSeleniumDownloader simpleSeleniumDownloader) {
        this.simpleSeleniumDownloader = simpleSeleniumDownloader;
    }


    @Autowired
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void crawl(int threadNum, String... baseUrl) {
        Spider spider = Spider.create(zhihuPageProcessor).addPipeline(pipeline).addUrl(baseUrl)
                .setDownloader(simpleSeleniumDownloader)
                .thread(threadNum);
        try {
            String fileCachePath = resource.getString("FILE_CACHE_PATH");
            log.info("use FileCacheQueueScheduler and FILE_CACHE_PATH is : " + fileCachePath);
            spider.setScheduler(new FileCacheQueueScheduler(fileCachePath));
        }catch (MissingResourceException e){
            log.info("no FILE_CACHE_PATH founds, default use QueueSchedule");
        }
        spider.run();
//        spider.setScheduler(new PriorityScheduler()
//                .setDuplicateRemover(new BloomFilterDuplicateRemover(10000000))).run();
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-dao.xml");
        ZhihuSpider zhihuSpider = context.getBean(ZhihuSpider.class);
        zhihuSpider.crawl(5, "https://www.zhihu.com/people/zhou-ruo-yu-99-95/following",
                "https://www.zhihu.com/people/zhou-ruo-yu-99-95/answers/by_votes",
                "https://www.zhihu.com/people/zhou-ruo-yu-99-95/posts/posts_by_votes");
    }
}
