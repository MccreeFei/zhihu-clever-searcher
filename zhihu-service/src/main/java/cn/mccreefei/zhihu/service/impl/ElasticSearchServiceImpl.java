package cn.mccreefei.zhihu.service.impl;

import cn.mccreefei.zhihu.dao.domain.AnswerWithUserDo;
import cn.mccreefei.zhihu.dao.domain.ArticleWithUserDo;
import cn.mccreefei.zhihu.dao.mapper.AnswerMapper;
import cn.mccreefei.zhihu.dao.mapper.ArticleMapper;
import cn.mccreefei.zhihu.dao.mapper.UserMapper;
import cn.mccreefei.zhihu.enums.SortCodeEnum;
import cn.mccreefei.zhihu.enums.TextTypeEnum;
import cn.mccreefei.zhihu.service.ElasticSearchService;
import cn.mccreefei.zhihu.service.TextService;
import cn.mccreefei.zhihu.service.vo.EsComparedSearchVo;
import cn.mccreefei.zhihu.service.vo.EsSearchVo;
import cn.mccreefei.zhihu.service.vo.TextVo;
import cn.mccreefei.zhihu.util.BeanUtils;
import cn.mccreefei.zhihu.util.HTMLUtil;
import cn.mccreefei.zhihu.util.ZhihuComparator;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author MccreeFei
 * @create 2018-01-22 14:59
 */
@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {
    @Resource
    private Client client;
    @Resource
    private TextService textService;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private Environment env;

    @Override
    public boolean importEsData() {
        XContentBuilder mappingBuilder = null;
        try {
            //mapping定义
            mappingBuilder = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("text")
                    .startObject("properties")
                    .startObject("answerId").field("type", "long").endObject()
                    .startObject("questionId").field("type", "long").endObject()
                    .startObject("articleId").field("type", "long").endObject()
                    .startObject("textType").field("type", "long").endObject()
                    .startObject("content").field("type", "text").field("analyzer", "ik_smart").endObject()
                    .endObject()
                    .endObject()
                    .endObject();
            //创建索引和映射
            CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate("zhihu")
                    .addMapping("text", mappingBuilder).get();

            //获取所有文本
            List<TextVo> allTextList = textService.getAllTextList();
            //去除所有html标签
            allTextList.forEach(textVo -> {
                textVo.setContent(HTMLUtil.delHTMLTag(textVo.getContent()));
            });

            //批量导入es
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
            ObjectMapper objectMapper = new ObjectMapper();
            allTextList.forEach(bean -> {
                try {
                    bulkRequestBuilder.add(client.prepareIndex("zhihu", "text")
                            .setSource(objectMapper.writeValueAsBytes(bean), XContentType.JSON));
                } catch (JsonProcessingException e) {
                    log.error("json转化失败！", e);
                }
            });

            BulkResponse bulkResponse = bulkRequestBuilder.get();
            if (bulkResponse.hasFailures()) {
                log.error("批量导入Es发生错误！");
                return false;
            }
        } catch (Exception e) {
            log.error("批量导入Es发生错误！", e);
            return false;
        }
        return true;
    }

    @Override
    public List<EsSearchVo> search(String content) {
        String[] includes = {"questionId", "answerId", "articleId", "textType"};
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch()
                .setSource(SearchSourceBuilder.searchSource().fetchSource(includes, new String[]{"content"}))
                .setQuery(QueryBuilders.matchQuery("content", content).analyzer("ik_max_word"))
                .setSize(15)
                .highlighter(new HighlightBuilder().field("content").preTags("<span class='my-high-light'>").postTags("</span>"));
        SearchResponse searchResponse = searchRequestBuilder.get();
        SearchHits hits = searchResponse.getHits();
        List<EsSearchVo> result = Lists.newLinkedList();

        hits.forEach(searchHit -> {
            //获得source
            EsSearchVo esSearchVo = JSON.parseObject(searchHit.getSourceAsString(), EsSearchVo.class);
            //每条匹配得分
            esSearchVo.setSearchScore((double) searchHit.getScore());
            TextTypeEnum textTypeEnum = TextTypeEnum.getByCode(esSearchVo.getTextType());
            if (textTypeEnum == null){
                log.error("文本类型错误！");
                return;
            }
            //回答
            if (TextTypeEnum.ANSWER.getCode() == textTypeEnum.getCode()){
                AnswerWithUserDo _do = AnswerWithUserDo.builder().answerId(esSearchVo.getAnswerId())
                        .questionId(esSearchVo.getQuestionId()).build();
                AnswerWithUserDo answerInfo = answerMapper.getAnswerInfo(_do);
                //填充esSearchVo
                esSearchVo.setSearchText(content);
                esSearchVo.setTextTitle(answerInfo.getQuestionTitle());
                esSearchVo.setTextAgrees(answerInfo.getTextAgrees());
                esSearchVo.setTextComments(answerInfo.getTextComments());
                esSearchVo.setTextUrl(answerInfo.getAnswerUrl());
                //user
                esSearchVo.setCharacterUrl(answerInfo.getCharacterUrl());
                esSearchVo.setHeadUrl(answerInfo.getHeadUrl());
                esSearchVo.setSimpleDescription(formatSimpleDesc(answerInfo.getSimpleDescription()));
                esSearchVo.setUserAgrees(answerInfo.getUserAgrees());
                esSearchVo.setUserCollects(answerInfo.getUserCollects());
                esSearchVo.setUserName(answerInfo.getUserName());
                esSearchVo.setUserThanks(answerInfo.getUserThanks());
                esSearchVo.setUserFollowers(answerInfo.getUserFollowers());
                esSearchVo.setUserFollowees(answerInfo.getUserFollowees());

            }
            //文章
            else if (TextTypeEnum.ARTICLE.getCode() == textTypeEnum.getCode()){
                ArticleWithUserDo _do = ArticleWithUserDo.builder().articleId(esSearchVo.getArticleId()).build();
                ArticleWithUserDo articleInfo = articleMapper.getArticleInfo(_do);
                //填充esSearchVo
                esSearchVo.setSearchText(content);
                esSearchVo.setTextTitle(articleInfo.getArticleTitle());
                esSearchVo.setCharacterUrl(articleInfo.getCharacterUrl());
                esSearchVo.setHeadUrl(articleInfo.getHeadUrl());
                esSearchVo.setTextAgrees(articleInfo.getTextAgrees());
                esSearchVo.setTextComments(articleInfo.getTextComments());
                esSearchVo.setSimpleDescription(formatSimpleDesc(articleInfo.getSimpleDescription()));
                esSearchVo.setTextUrl(articleInfo.getArticleUrl());
                esSearchVo.setUserAgrees(articleInfo.getUserAgrees());
                esSearchVo.setUserCollects(articleInfo.getUserCollects());
                esSearchVo.setUserName(articleInfo.getUserName());
                esSearchVo.setUserThanks(articleInfo.getUserThanks());
                esSearchVo.setUserFollowers(articleInfo.getUserFollowers());
                esSearchVo.setUserFollowees(articleInfo.getUserFollowees());

            }
            //获取高亮文本
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField contentField = highlightFields.get("content");
            List<String> highLightTexts = Lists.newArrayList();
            if (contentField != null){
                Text[] fragments = contentField.getFragments();
                if (fragments != null){
                    for (Text fragment : fragments){
                        highLightTexts.add(fragment.toString());
                    }
                }
            }
            esSearchVo.setHighLightTexts(highLightTexts);
            result.add(esSearchVo);
        });
        return result;
    }

    @Override
    public List<EsComparedSearchVo> sortSearchText(List<EsSearchVo> searchVoList, Integer sortCode) {
        List<EsComparedSearchVo> result = Lists.newLinkedList();
        if (!CollectionUtils.isEmpty(searchVoList)){
            searchVoList.forEach(_vo -> {
                EsComparedSearchVo esComparedSearchVo = generateComparedScore(_vo);
                result.add(esComparedSearchVo);
            });
        }
        if (sortCode == null || SortCodeEnum.GENERAL_SORT.getCode().equals(sortCode)){
            Collections.sort(result, ZhihuComparator.generalComparator);
        }else if (SortCodeEnum.MATCH_SORT.getCode().equals(sortCode)){
            Collections.sort(result, ZhihuComparator.matchComparator);
        }else if (SortCodeEnum.TEXT_SORT.getCode().equals(sortCode)){
            Collections.sort(result, ZhihuComparator.textComparator);
        }else if (SortCodeEnum.USER_SORT.getCode().equals(sortCode)){
            Collections.sort(result, ZhihuComparator.userComparator);
        }
        return result;
    }

    //对于作者简介，大于30字符进行截断
    private String formatSimpleDesc(String desc){
        if (desc == null || desc.length() <= 30){
            return desc;
        }
        return desc.substring(0, 30) + "...";
    }

    private EsComparedSearchVo generateComparedScore(EsSearchVo searchVo){
        if (searchVo == null) return null;
        EsComparedSearchVo result = BeanUtils.propertiesCopy(searchVo, EsComparedSearchVo.class);
        double matchAccount = Double.parseDouble(env.getProperty("account.match"));
        double textAccount = Double.parseDouble(env.getProperty("account.text"));
        double userAccount = Double.parseDouble(env.getProperty("account.user"));

        Optional<Integer> userAgrees = Optional.ofNullable(searchVo.getUserAgrees());
        Optional<Integer> userThanks = Optional.ofNullable(searchVo.getUserThanks());
        Optional<Integer> userCollects = Optional.ofNullable(searchVo.getUserCollects());
        Optional<Integer> userFollowers = Optional.ofNullable(searchVo.getUserFollowers());
        double userScore = (userAgrees.orElse(500) + userThanks.orElse(500) + userCollects.orElse(500)
                + userFollowers.orElse(500))/8000;


        Optional<Integer> textAgrees = Optional.ofNullable(searchVo.getTextAgrees());
        Optional<Integer> textComments = Optional.ofNullable(searchVo.getTextComments());
        double textScore = (textAgrees.orElse(500) + textComments.orElse(100))/100;
        double matchScore = searchVo.getSearchScore() * 2.5;
        double comparedScore = userScore * userAccount + matchScore * matchAccount + textScore * textAccount;
        result.setUserScore((int) userScore);
        result.setTextScore((int) textScore);
        result.setMatchScore((int) matchScore);
        result.setComparedScore((int) comparedScore);

        return result;
    }
}
