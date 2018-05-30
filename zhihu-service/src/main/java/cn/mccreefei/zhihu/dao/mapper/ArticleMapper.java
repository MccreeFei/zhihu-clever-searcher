package cn.mccreefei.zhihu.dao.mapper;

import cn.mccreefei.zhihu.dao.domain.ArticleWithUserDo;

/**
 * @author MccreeFei
 * @create 2018-01-23 13:48
 */
public interface ArticleMapper {
    ArticleWithUserDo getArticleInfo(ArticleWithUserDo articleWithUserDo);
}
