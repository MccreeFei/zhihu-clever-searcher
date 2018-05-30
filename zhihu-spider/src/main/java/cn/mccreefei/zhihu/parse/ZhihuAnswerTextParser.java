package cn.mccreefei.zhihu.parse;

import cn.mccreefei.zhihu.model.ZhihuAnswerText;
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
public class ZhihuAnswerTextParser {
    public void parseAnswerTextInfo(Page page) {
        String url = page.getRequest().getUrl();
        try {
            Integer[] questionAndAnswerId = getQuestionAndAnswerId(url);
            Integer questionId = questionAndAnswerId[0];
            Integer answerId = questionAndAnswerId[1];

            String content = page.getHtml().xpath("//div[@class='Question-main']//div[@class='RichContent-inner']/span").get();

            Date date = new Date();
            ZhihuAnswerText answerText = new ZhihuAnswerText.AnswerTextBuilder().setQuestionId(questionId).setAnswerId(answerId)
                    .setContent(content).setCreateTime(date).setModifyTime(date).build();
            page.putField("answerText", answerText);
        }catch (Exception e){
            log.error("parse answer text failed!", e);
        }

    }



    private Integer[] getQuestionAndAnswerId(String answerUrl) throws IndexOutOfBoundsException {
        Integer[] result = new Integer[2];
        int i = answerUrl.indexOf("/question/");
        int j = answerUrl.indexOf("/answer/");

        result[0] = Integer.valueOf(answerUrl.substring(i + 10, j));
        result[1] = Integer.valueOf(answerUrl.substring(j + 8));
        return result;
    }
}
