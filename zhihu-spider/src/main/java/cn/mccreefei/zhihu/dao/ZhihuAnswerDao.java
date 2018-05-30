package cn.mccreefei.zhihu.dao;

import cn.mccreefei.zhihu.model.ZhihuAnswer;

import java.util.List;

/**
 * @author MccreeFei
 * @create 2017-11-10 10:31
 */
public interface ZhihuAnswerDao {
    public int addZhihuAnswer(ZhihuAnswer answer);

    public List<String> getZhihuAnswerUrlList();
}
