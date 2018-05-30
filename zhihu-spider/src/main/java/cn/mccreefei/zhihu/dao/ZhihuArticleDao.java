package cn.mccreefei.zhihu.dao;

import cn.mccreefei.zhihu.model.ZhihuArticle;

import java.util.List;

/**
 * @author MccreeFei
 * @create 2017-11-15 10:18
 */
public interface ZhihuArticleDao {
    public int addZhihuArticle(ZhihuArticle article);

    public List<String> getZhihuArticleUrlList();
}
