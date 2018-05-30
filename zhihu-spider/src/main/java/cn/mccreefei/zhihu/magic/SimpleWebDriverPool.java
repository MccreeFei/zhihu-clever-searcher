package cn.mccreefei.zhihu.magic;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author MccreeFei
 * @create 2017-11-21 9:39
 */
@Slf4j
public class SimpleWebDriverPool {
    private final int capacity;
    private final static int DEFAULT_CAPACITY = 5;
    private final BlockingQueue<WebDriver> innerQueue;
    private final static String DRIVER_PHANTOMJS = "phantomjs";
    private final static String DRIVER_CHROME = "chrome";
    private final static AtomicInteger refCount = new AtomicInteger(0);
    private String driver;
    private final static ResourceBundle resource = ResourceBundle.getBundle("zhihu");

    public SimpleWebDriverPool() {
        this(DEFAULT_CAPACITY);
    }

    public SimpleWebDriverPool(int capacity) {
        this.capacity = capacity;
        innerQueue = new LinkedBlockingQueue<WebDriver>(capacity);
    }

    private WebDriver configureWebDriver() {
        WebDriver webDriver = null;
        try {
            driver = resource.getString("DRIVER");
        } catch (MissingResourceException e) {
            log.debug("no driver property find use default driver phantomjs");
            driver = DRIVER_PHANTOMJS;
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability("takeScreenshot", false);
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX
                        + "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
                "--load-images=no");

        if (isUrl(driver)) {
            try {
                URL remoteAddress = new URL(driver);
                webDriver = new RemoteWebDriver(remoteAddress, caps);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (DRIVER_CHROME.equalsIgnoreCase(driver)) {
            String chromePath = resource.getString("CHROME_DRIVER_PATH");
            System.setProperty("webdriver.chrome.driver", chromePath);
            webDriver = new ChromeDriver(caps);
        } else {
            String phantomjsPath = resource.getString("PHANTOMJS_PATH");
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjsPath);
            webDriver = new PhantomJSDriver(caps);
        }
        return webDriver;
    }

    private boolean isUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public WebDriver get() throws InterruptedException {
        WebDriver driver = null;
        if ((driver = innerQueue.poll()) != null) {
            return driver;
        }
        if (refCount.get() < capacity) {
            synchronized (innerQueue) {
                if (refCount.get() < capacity) {
                    WebDriver webDriver = configureWebDriver();
                    log.info("add webDriver {} into pool!", webDriver);
                    refCount.incrementAndGet();
                    innerQueue.add(webDriver);
                }
            }
        }

        return innerQueue.take();
    }

    public void returnToPool(WebDriver webDriver) {
        innerQueue.add(webDriver);
    }


    public void closeAll() {
        synchronized (innerQueue) {
            for (WebDriver webDriver : innerQueue) {
                log.info("Quit WebDriver: " + webDriver);
                webDriver.quit();
                webDriver = null;
            }
        }
    }

}
