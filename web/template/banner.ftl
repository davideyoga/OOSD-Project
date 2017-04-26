<!--banner-->
<div class="banner">

    <h2>
        <a href="index.ftl">Home</a>
        <#if .main_template_name == "index.ftl">

        <#else>
            <i class="fa fa-angle-right"></i>
            <span>${.main_template_name}</span>
        </#if>
    </h2>
</div>
<!--//banner-->