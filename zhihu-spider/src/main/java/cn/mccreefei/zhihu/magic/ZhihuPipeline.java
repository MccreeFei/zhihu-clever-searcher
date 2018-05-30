package cn.mccreefei.zhihu.magic;

import cn.mccreefei.zhihu.dao.*;
import cn.mccreefei.zhihu.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author MccreeFei
 * @create 2017-11-15 13:22
 */
@Slf4j
@Component
public class ZhihuPipeline implements Pipeline {
    private ZhihuUserDao userDao;
    private ZhihuArticleDao articleDao;
    private ZhihuAnswerDao answerDao;
    private ZhihuArticleTextDao articleTextDao;
    private ZhihuAnswerTextDao answerTextDao;

    @Autowired
    public void setArticleTextDao(ZhihuArticleTextDao articleTextDao) {
        this.articleTextDao = articleTextDao;
    }

    @Autowired
    public void setAnswerTextDao(ZhihuAnswerTextDao answerTextDao) {
        this.answerTextDao = answerTextDao;
    }

    @Autowired
    public void setUserDao(ZhihuUserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setArticleDao(ZhihuArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Autowired
    public void setAnswerDao(ZhihuAnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        Object user = resultItems.getAll().get("user");
        Object article = resultItems.getAll().get("article");
        Object answer = resultItems.getAll().get("answer");
        Object articleText = resultItems.getAll().get("articleText");
        Object answerText = resultItems.getAll().get("answerText");

        if (user != null) {
            int i = userDao.addZhihuUser((ZhihuUser) user);
            if (i == 1) {
                log.info("add one user record success : {}", user);
            } else if (i == 2) {
                log.info("update one user record success : {}", user);
            } else {
                log.error("add user record failed!");
            }
        }
        if (article != null) {
            int i = articleDao.addZhihuArticle((ZhihuArticle) article);
            if (i == 1) {
                log.info("add one article record success : {}", article);
            } else if (i == 2) {
                log.info("update one article record success : {}", article);
            } else {
                log.error("add article record failed!");
            }
        }
        if (answer != null) {
            int i = answerDao.addZhihuAnswer((ZhihuAnswer) answer);
            if (i == 1) {
                log.info("add one answer record success : {}", answer);
            } else if (i == 2) {
                log.info("update one answer record success : {}", answer);
            } else {
                log.error("add answer record failed!");
            }
        }
        if (articleText != null) {
            int i = articleTextDao.addZhihuArticleTextInfo((ZhihuArticleText) articleText);
            if (i == 1) {
                log.info("add one articleText record success : {}", articleText);
            } else if (i == 2) {
                log.info("update one articleText record success : {}", articleText);
            } else {
                log.error("add articleText record failed!");
            }
        }
        if (answerText != null) {
            int i = answerTextDao.addZhihuAnswerTextInfo((ZhihuAnswerText) answerText);
            if (i == 1) {
                log.info("add one answerText record success : {}", answerText);
            } else if (i == 2) {
                log.info("update one answerText record success : {}", answerText);
            } else {
                log.error("add answerText record failed!");
            }
        }
    }
}
