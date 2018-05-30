<!doctype html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en-US"> <![endif]-->
<!--[if IE 7]>    <html class="lt-ie9 lt-ie8" lang="en-US"> <![endif]-->
<!--[if IE 8]>    <html class="lt-ie9" lang="en-US"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en-US"> <!--<![endif]-->
<head>
    <!-- META TAGS -->
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>ZhihuSearcher</title>

    <link rel="shortcut icon" href="/zhihusearcher/images/favicon.png"/>


    <!-- Style Sheet-->
    <link rel="stylesheet" href="/zhihusearcher/style.css"/>
    <link rel='stylesheet' id='bootstrap-css-css' href='/zhihusearcher/css/bootstrap5152.css?ver=1.0' type='text/css' media='all'/>
    <link rel='stylesheet' id='responsive-css-css' href='/zhihusearcher/css/responsive5152.css?ver=1.0' type='text/css' media='all'/>
    <link rel='stylesheet' id='pretty-photo-css-css' href='/zhihusearcher/js/prettyphoto/prettyPhotoaeb9.css?ver=3.1.4' type='text/css'
          media='all'/>
    <link rel='stylesheet' id='main-css-css' href='/zhihusearcher/css/main5152.css?ver=1.0' type='text/css' media='all'/>
    <link rel='stylesheet' id='custom-css-css' href='/zhihusearcher/css/custom5152.html?ver=1.0' type='text/css' media='all'/>
    <link rel="stylesheet" type="text/css" href="/zhihusearcher/css/jquery.selectlist.css">


    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="/zhihusearcher/js/html5.js"></script></script>
    <![endif]-->


</head>

<body>

<!-- Start of Header -->
<div class="header-wrapper">
    <header>
        <div class="container">


            <div class="logo-container">
                <!-- Website Logo -->
                <a href="/zhihusearcher" title="ZhihuSearcher" style="color: #ffffff ;font-size: medium">
                    ZhihuSearcher
                </a>
                <span class="tag-line">知乎智能搜索</span>
            </div>

            <nav class="main-nav">
                <div class="menu-top-menu-container">
                    <ul id="menu-top-menu" class="clearfix">
                        <li><a href="/zhihusearcher/about">About</a> </li>
                        <li><a href="/zhihusearcher/contact">Contact</a> </li>
                    </ul>
                </div>
            </nav>

            <!-- Start of Main Navigation -->
            <#--<nav class="main-nav">-->
                <#--<div class="menu-top-menu-container">-->
                    <#--<ul id="menu-top-menu" class="clearfix">-->
                        <#--<li><a href="index-2.html">Home</a></li>-->
                        <#--<li><a href="home-categories-description.html">Home 2</a></li>-->
                        <#--<li><a href="home-categories-articles.html">Home 3</a></li>-->
                        <#--<li class="current-menu-item"><a href="articles-list.html">Articles List</a></li>-->
                        <#--<li><a href="faq.html">FAQs</a></li>-->
                        <#--<li><a href="#">Skins</a>-->
                            <#--<ul class="sub-menu">-->
                                <#--<li><a href="blue-skin.html">Blue Skin</a></li>-->
                                <#--<li><a href="green-skin.html">Green Skin</a></li>-->
                                <#--<li><a href="red-skin.html">Red Skin</a></li>-->
                                <#--<li><a href="index-2.html">Default Skin</a></li>-->
                            <#--</ul>-->
                        <#--</li>-->
                        <#--<li><a href="#">More</a>-->
                            <#--<ul class="sub-menu">-->
                                <#--<li><a href="full-width.html">Full Width</a></li>-->
                                <#--<li><a href="elements.html">Elements</a></li>-->
                                <#--<li><a href="page.html">Sample Page</a></li>-->
                            <#--</ul>-->
                        <#--</li>-->
                        <#--<li><a href="contact.html">Contact</a></li>-->
                    <#--</ul>-->
                <#--</div>-->
            <#--</nav>-->
            <!-- End of Main Navigation -->

        </div>
    </header>
</div>
<!-- End of Header -->

<!-- Start of Search Wrapper -->
<div class="search-area-wrapper">
    <div class="search-area container">
        <h3 class="search-header">Have a Question?</h3>
        <p class="search-tag-line">
            <!-- If you have any question you can ask below or enter what you are looking for! -->
            Have Fun in Searching！
        </p>

        <form id="search-form" class="search-form clearfix" method="post" action="/zhihusearcher" autocomplete="off">
            <input class="search-term required" type="text" id="content" name="content"
                   placeholder="Type your search terms here"
                   <#if searchContent??>value="${searchContent}"</#if>/>
            <input class="search-btn" type="submit" value="Search"/>
            <div id="search-error-container"></div>
        </form>
    </div>
</div>
<!-- End of Search Wrapper -->
<div>
    <select id="order" name="order">
        <option value="0" <#if sort?? && sort==0>selected="selected"</#if>>综合排序</option>
        <option value="1" <#if sort?? && sort==1>selected="selected"</#if>>文本匹配排序</option>
        <option value="2" <#if sort?? && sort==2>selected="selected"</#if>>文本影响度排序</option>
        <option value="3" <#if sort?? && sort==3>selected="selected"</#if>>作者影响度排序</option>
    </select>
</div>

<!-- Start of Page Container -->
<div class="page-container">
    <div class="container">
        <div class="row">

            <!-- start of page content -->
            <div class="span8 main-listing">
            <#if results?? && (results?size > 0)>
                <#list results as result>
                    <article class="format-standard type-post hentry clearfix">

                        <header class="clearfix">

                            <h3 class="post-title">
                                <a href="${result.textUrl!"#"}" target="_blank">${result.textTitle!""}</a>
                            </h3>

                            <div class="post-meta clearfix">
                                    <span class="author"><a href="https://www.zhihu.com/people/${result.characterUrl}"
                                                            target="_blank">
                                    ${result.userName!""}</a></span>
                                <span class="category">${result.simpleDescription!""}</span>
                                <span class="comments">${result.textComments!"?"} Comments</span>
                                <span class="like-count">${result.textAgrees!"?"}</span>
                            </div>

                            <div class="post-meta clearfix">
                                <span class="category">综合得分:<strong>${result.comparedScore}</strong></span>
                                <span class="category">文本匹配得分:<strong>${result.matchScore}</strong></span>
                                <span class="category">文本影响度得分:<strong>${result.textScore}</strong></span>
                                <span class="category">作者影响度得分:<strong>${result.userScore}</strong></span>
                            </div>

                        </header>
                        <#if result.highLightTexts?? &&(result.highLightTexts?size > 0)>
                            <#list result.highLightTexts as highLightText>
                                <p>${highLightText!""}</p>
                            </#list>
                        </#if>

                    </article>

                </#list>
            </#if>
            </div>
            <!-- end of page content -->


            <!-- start of sidebar -->
            <aside class="span4 page-sidebar">

                <section class="widget">
                    <div class="support-widget">
                        <h3 class="title">Tips</h3>
                        <p class="intro">数据来源知乎，各项数据具有时效性仅供参考。</p>
                        <p class="intro">综合排序说明：综合排序基于文本匹配、文本影响度以及作者影响度指标，其中文本匹配占比权重70%，文本影响度20%，作者影响度10%。</p>
                    </div>
                </section>
            </aside>
            <!-- end of sidebar -->
        </div>
    </div>
</div>
<!-- End of Page Container -->

<!-- Start of Footer -->
<footer id="footer-wrapper">

    <!-- Footer Bottom -->
    <div id="footer-bottom-wrapper">
        <div id="footer-bottom" class="container">
            <div class="row">
                <div class="span6">
                    <p class="copyright">
                        © 2018 Powered by <a style="color: #ffffff" href="http://www.mccreefei.cn" target="_blank">www.mccreefei.cn</a>
                    </p>
                </div>

            </div>
        </div>
    </div>
    <!-- End of Footer Bottom -->

</footer>
<!-- End of Footer -->

<a href="#top" id="scroll-top"></a>

<!-- script -->
<script type='text/javascript' src='/zhihusearcher/js/jquery-1.8.3.min.js'></script>
<script type="text/javascript" src="/zhihusearcher/js/jquery.selectlist.js"></script>
<script type='text/javascript' src='/zhihusearcher/js/jquery.easing.1.34e44.js?ver=1.3'></script>
<script type='text/javascript' src='/zhihusearcher/js/prettyphoto/jquery.prettyPhotoaeb9.js?ver=3.1.4'></script>
<script type='text/javascript' src='/zhihusearcher/js/jquery.liveSearchd5f7.js?ver=2.0'></script>
<script type='text/javascript' src='/zhihusearcher/js/jflickrfeed.js'></script>
<script type='text/javascript' src='/zhihusearcher/js/jquery.formd471.js?ver=3.18'></script>
<script type='text/javascript' src='/zhihusearcher/js/jquery.validate.minfc6b.js?ver=1.10.0'></script>
<script type='text/javascript' src='/zhihusearcher/js/custom5152.js?ver=1.0'></script>

<script type="text/javascript">
    $("#order").change(function () {
        $("#search-form").attr("action", "/zhihusearcher?sort=" + $('#order').val());
        var content = $("#content").val();
        if (content == null || content == '') {
            return false;
        } else {
            $("#search-form").submit();
        }
    })
</script>


</body>
</html>
