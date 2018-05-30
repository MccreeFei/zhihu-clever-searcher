package cn.mccreefei.zhihu.dao.mapper;

import cn.mccreefei.zhihu.dao.domain.AnswerWithUserDo;

/**
 * @author MccreeFei
 * @create 2018-01-23 10:39
 */
public interface AnswerMapper {
    AnswerWithUserDo getAnswerInfo(AnswerWithUserDo answerWithUserDo);
}
