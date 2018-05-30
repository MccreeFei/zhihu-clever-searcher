package cn.mccreefei.zhihu.service.impl;

import cn.mccreefei.zhihu.dao.domain.TextDo;
import cn.mccreefei.zhihu.dao.mapper.TextMapper;
import cn.mccreefei.zhihu.service.TextService;
import cn.mccreefei.zhihu.service.vo.TextVo;
import cn.mccreefei.zhihu.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-01-22 9:45
 */
@Service
public class TextServiceImpl implements TextService {
    @Resource
    private TextMapper textMapper;

    @Override
    public List<TextVo> getAllTextList() {
        List<TextDo> allTextList = textMapper.getAllTextList();
        return BeanUtils.collectionCopy(allTextList, TextVo.class);
    }
}
