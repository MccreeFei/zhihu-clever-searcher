package cn.mccreefei.web.controller;

import cn.mccreefei.zhihu.enums.SortCodeEnum;
import cn.mccreefei.zhihu.service.ElasticSearchService;
import cn.mccreefei.zhihu.service.vo.EsComparedSearchVo;
import cn.mccreefei.zhihu.service.vo.EsSearchVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author MccreeFei
 * @create 2018-01-24 16:38
 */
@Controller
@RequestMapping("/zhihusearcher")
public class MainController {
    @Resource
    private ElasticSearchService elasticSearchService;

    @RequestMapping(value = "")
    public String index(@RequestParam(value = "sort", required = false) Integer sortCode,
            @RequestParam(value = "content", required = false) String content, Model model){
        if (!StringUtils.isEmpty(content)){
            SortCodeEnum sortCodeEnum = Optional.ofNullable(SortCodeEnum.getByCode(sortCode)).orElse(SortCodeEnum.GENERAL_SORT);
            List<EsSearchVo> esSearchVoList = elasticSearchService.search(content);
            List<EsComparedSearchVo> esComparedSearchVoList = elasticSearchService.sortSearchText(esSearchVoList, sortCodeEnum.getCode());
            model.addAttribute("results", esComparedSearchVoList);
        }
        model.addAttribute("sort", Optional.ofNullable(sortCode).orElse(0));
        model.addAttribute("searchContent", content);
        return "index";
    }

    @RequestMapping("/contact")
    public String contact(){
        return "contact";
    }

    @RequestMapping("/about")
    public String about(){
        return "about";
    }

    @RequestMapping("/import")
    @ResponseBody
    public String importEs(){
        boolean b = elasticSearchService.importEsData();
        return String.valueOf(b);
    }
}
