package cn.mccreefei.zhihu.service;

import cn.mccreefei.zhihu.service.vo.EsComparedSearchVo;
import cn.mccreefei.zhihu.service.vo.EsSearchVo;

import java.util.Comparator;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2018-01-22 14:58
 */
public interface ElasticSearchService {
    /**
     * 向Es导入所有文本数据
     *
     * @return true成功 false失败
     */
    public boolean importEsData();

    /**
     * 关键文本搜索
     */
    public List<EsSearchVo> search(String content);

    /**
     * 综合排序
     */
    public List<EsComparedSearchVo> sortSearchText(List<EsSearchVo> searchVoList, Integer sortCode);

}
