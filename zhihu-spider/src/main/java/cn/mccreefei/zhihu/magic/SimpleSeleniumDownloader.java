package cn.mccreefei.zhihu.magic;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Modify on us.codecraft.webmagic.downloader.selenium.SeleniumDownloader
 * Now support Chrome driver、Remote driver with Ghost driver and Ghost driver with phantomJS
 *
 * @author MccreeFei
 * @create 2017-11-17 9:51
 */
@Slf4j
public class SimpleSeleniumDownloader implements Downloader, Closeable {
    private int driverType;
    private DesiredCapabilities cap;
    private URL ghostDriverUrl;
    private final static ResourceBundle resource = ResourceBundle.getBundle("zhihu");
    private SimpleWebDriverPool webDriverPool;
    private int sleepTime = 3000;
    private int poolSize = 5;


    /**
     * set sleep time to wait until load success
     *
     * @param sleepTime sleepTime
     * @return this
     */
    public SimpleSeleniumDownloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    @Override
    public Page download(Request request, Task task) {
        long start = System.currentTimeMillis();
        checkInit();
        WebDriver webDriver = null;
        try {
            webDriver = webDriverPool.get();
            log.info("downloading page " + request.getUrl());
            webDriver.get(request.getUrl());
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriver.Options manage = webDriver.manage();
        Site site = task.getSite();
        if (site.getCookies() != null) {
            for (Map.Entry<String, String> cookieEntry : site.getCookies()
                    .entrySet()) {
                Cookie cookie = new Cookie(cookieEntry.getKey(),
                        cookieEntry.getValue());
                manage.addCookie(cookie);
            }
        }

        long end = System.currentTimeMillis();
        log.info("download page spend {} ms, url is {}", (end - start), request.getUrl());

		/*
         * TODO You can add mouse event or other processes
		 *
		 */

        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        String content = webElement.getAttribute("outerHTML");
        Page page = new Page();
        page.setRawText(content);
        page.setHtml(new Html(content, request.getUrl()));
        page.setUrl(new PlainText(request.getUrl()));
        page.setRequest(request);
        webDriverPool.returnToPool(webDriver);
        return page;
    }

    private void checkInit() {
        if (webDriverPool == null) {
            synchronized (this) {
                webDriverPool = new SimpleWebDriverPool(poolSize);
            }
        }
    }

    @Override
    public void setThread(int threadNum) {
        this.poolSize = threadNum;
    }


    @Override
    public void close() throws IOException {
        webDriverPool.closeAll();
    }
}
