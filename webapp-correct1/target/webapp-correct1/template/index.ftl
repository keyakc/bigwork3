<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<#assign listType = RequestParameters['type']>
<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <div class="tab">
            <ul>
                <li <#if !listType || listType != 1>class="z-sel"</#if> ><a href="index">所有内容</a></li>
                <#if user && user.usertype == 0><li <#if listType == 1>class="z-sel"</#if> ><a href="type">未购买的内容</a></li></#if>
            </ul>
        </div>
    </div>
    <#if !productList || !productList?has_content>
    <div class="n-result">
        <h3>暂无内容！</h3>
    </div>
    <#else>
    <div class="n-plist">
        <ul class="f-cb" id="plist">
        <#if user && user.usertype == 0 && listType == 1>
            <#list productList as x>
                <#if x.isBuy==0>
                <li id="p-${x.id}">
                    <a href="show?id=${x.id}" class="link">
                        <div class="img"><img src="${x.image}" alt="${x.title}"></div>
                        <h3>${x.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price}</span></div>
                    </a>
                </li>
                </#if>
            </#list>
        <#else>
            <#list productList as x>
                <li id="p-${x.id}">
                    <a href="show?id=${x.id}" class="link">
                        <div class="img"><img src="${x.image}" alt="${x.title}"></div>
                        <h3>${x.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price}</span></div>
                        <#if user && user.usertype==0 && x.isBuy==1><span class="had"><b>已购买${x.isBuy}</b></span></#if>
                        <#if user && user.usertype==1 && x.isSell==1><span class="had"><b>已售出${x.isSell}</b></span></#if>
                    </a>
                    <#if user && user.usertype==1 && x.isSell==0><span class="u-btn u-btn-normal u-btn-xs del" data-del="${x.id}">删除</span></#if>
                </li>
            </#list>
        </#if>
        </ul>
    </div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="js/global.js"></script>
<script type="text/javascript" src="js/pageIndex.js"></script>
</body>
</html>