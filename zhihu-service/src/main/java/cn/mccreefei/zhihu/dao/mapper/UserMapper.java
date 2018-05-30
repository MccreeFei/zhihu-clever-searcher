package cn.mccreefei.zhihu.dao.mapper;

import cn.mccreefei.zhihu.dao.domain.UserDo;

import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-01-19 16:38
 */
public interface UserMapper {
    List<UserDo> getUserList(UserDo userDo);

    int maxUserScore();
}
