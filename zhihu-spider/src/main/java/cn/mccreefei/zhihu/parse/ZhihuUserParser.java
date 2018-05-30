package cn.mccreefei.zhihu.parse;

import cn.mccreefei.zhihu.model.ZhihuUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.Date;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2017-11-15 14:08
 */
@Slf4j
@Component
public class ZhihuUserParser {
    public void parseUserInfo(Page page){
        //特征url
        String characterUrl = getCharacterUrl(page.getRequest().getUrl());
        //头像url
        String headUrl = page.getHtml().xpath("//div[@class='UserAvatar ProfileHeader-avatar']/img/@src").get();
        //用户名
        String userName = page.getHtml().xpath("//h1[@class='ProfileHeader-title']/span[@class='ProfileHeader-name']/text()").get();
        //一句话简介
        String simpleDesc = page.getHtml().xpath("//h1[@class='ProfileHeader-title']/span[@class='RichText ProfileHeader-headline']/text()").get();

        Selectable sideColumns = page.getHtml().xpath("//div[@class='Profile-main']//div[@class='Profile-sideColumnItems']");
        List<String> agreeList = sideColumns.xpath("//div[@class='IconGraf']/text()").all();
        List<String> thankList = sideColumns.xpath("//div[@class='Profile-sideColumnItemValue']/text()").all();
        //赞同数
        Integer agrees = getAgrees(page.getRequest().getUrl(), agreeList);
        Integer[] thanksAndCollects = getThanksAndCollects(page.getRequest().getUrl(), thankList);
        //感谢数
        Integer thanks = thanksAndCollects[0];
        //被收藏数
        Integer collects = thanksAndCollects[1];

        Integer[] followersAndFollowees = getFollowersAndFollowees(page);
        //关注者
        Integer followers = followersAndFollowees[0];
        //关注了
        Integer followees = followersAndFollowees[1];


        ZhihuUser user = new ZhihuUser.UserBuilder().setCharacterUrl(characterUrl).setSimpleDescription(simpleDesc)
                .setHeadUrl(headUrl).setUserName(userName).setAgrees(agrees).setThanks(thanks).setCollects(collects)
                .setFollowers(followers).setFollowees(followees).setCreateTime(new Date()).setModifyTime(new Date()).build();

        page.putField("user", user);


    }

    private Integer getAgrees(String url, List<String> agreeList){
        Integer agrees = null;
        if (agreeList != null && agreeList.size() > 0){
            for (String temp : agreeList){
                if (StringUtils.isBlank(temp)){
                    continue;
                }
                int endIndex = temp.indexOf("次赞同") - 1;
                if (endIndex < 0) continue;
                int startIndex = temp.indexOf("获得") + 3;
                try {
                    agrees = Integer.valueOf(temp.substring(startIndex, endIndex).replaceAll(",", ""));
                }catch (Exception e){
                    log.warn("获取赞同数失败,页面：{},出错原因：{}",url, e);
                }
            }
        }
        return agrees;

    }

    private Integer[] getThanksAndCollects(String url, List<String> list){
        Integer[] result = new Integer[2];
        if (list != null && list.size() > 0){
            for (String temp : list){
                if (StringUtils.isBlank(temp))
                    continue;
                int thankEndIndex = temp.indexOf("次感谢") - 1;
                if (thankEndIndex < 0) continue;
                int thankStartIndex = temp.indexOf("获得") + 3;
                int collectEndIndex = temp.indexOf("次收藏") - 1;
                int collectStartIndex = thankEndIndex + 5;
                try {
                    result[0] = Integer.valueOf(temp.substring(thankStartIndex, thankEndIndex).replaceAll(",", ""));
                    result[1] = Integer.valueOf(temp.substring(collectStartIndex, collectEndIndex).replaceAll(",", ""));
                }catch (Exception e){
                    log.warn("获得感谢赞同数失败,页面：{},出错原因:{}",url, e);
                }
            }
        }
        return result;
    }

    private Integer[] getFollowersAndFollowees(Page page){
        Integer[] result = new Integer[2];
        List<String> list = page.getHtml().xpath("//div[@class='Card FollowshipCard']//strong[@class='NumberBoard-itemValue']//text()").all();
        if (list == null || list.size() != 2){
            log.warn("获取关注者失败,失败页面：{}", page.getRequest().getUrl());
        }else {
            result[0] = Integer.valueOf(list.get(1).replaceAll(",", ""));
            result[1] = Integer.valueOf(list.get(0).replaceAll(",", ""));
        }
        return result;
    }

    private String getCharacterUrl(String url){
        int startIndex = url.indexOf("people/") + 7;
        if (startIndex < 7){
            startIndex = url.indexOf("org/") + 4;
        }
        int endIndex = url.indexOf("/following");
        String result = null;
        try {
            result = url.substring(startIndex, endIndex);
        }catch (Exception e){
            log.warn("获取特征url失败，url:{}", url);
        }
        return result;
    }

}
