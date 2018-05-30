# ZhihuSearcher
基于知乎数据的智能搜索，将一个文本量化为文本影响度、作者影响度、文本匹配度三个指标，其中文本影响度由文本的获赞数和评论数计算得出，作者影响度由啊作者的获赞数、感谢数、粉丝数等计算得出，文本匹配度取自ElasticSearch。基于各个指标以相应的权重计算出综合分数，以此作为排序的依据。
![ZhihuSearcher](https://github.com/MccreeFei/zhihu-clever-searcher/blob/master/zhihu-searcher-web/src/main/resources/static/zhihusearcher/images/zhihu.png)

## 说明
ZhihuSearcher分为三个模块：

1. zhihu-spider 爬虫模块，使用JAVA开源框架WebMagic爬取知乎网站相关数据。WebMagic框架使用移步[Github](https://github.com/code4craft/webmagic/)。另外提供本人爬取到的部分数据仅供参考，sql文件位置`/sql/zhihu.sql`，mysql导入即可使用。
2. zhihu-service 服务模块，使用ElasticSearch完成查询排序逻辑。ElasticSearch下载配置请自行查阅相关文档，需要注意的是ElasticSearch默认分词器是不支持中文的，所以需要配置中文分词器IK，IK分词器下载配置移步[Github](https://github.com/medcl/elasticsearch-analysis-ik)。ElasticSearch运行成功后请在项目的配置文件中更改ElasticSearch相应的地址、结点信息。
3. zhihu-searcher-web web模块，负责接收客户端请求以及相应请求，采用SpringBoot框架。运行后先调取一次`/zhihusearcher/import`接口，将mysql中的文本信息导入到ElasticSearch当中提供查询，导入时间可能有点长，请耐心等待。