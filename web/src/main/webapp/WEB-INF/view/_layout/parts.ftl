<#import "functions.ftl" as functions />

<#macro contentEditor content url=''>
    <#-- @ftlvariable name="content" type="ru.atott.combiq.dao.entity.MarkdownContent" -->

    <#if functions.hasRoleSaOrContenter()>
        <co-contenteditor params="
                markdown: '${(content.markdown)!?html?js_string}',
                html: '${(content.html)!?html?js_string}',
                url: '${functions.if(url == '', "/content/" + content.id!, url)}'">
        </co-contenteditor>
    <#else>
        ${(content.html)!''}
    </#if>
</#macro>

<#macro questionLevel level class=''>
    <span class="co-level ${class} co-level-${level?lower_case}">
        <a title="Показать все вопросы с ${explainLevel(level)} уровнем" href="/questions/level/${level}">${level}</a>
    </span>
</#macro>

<#function explainLevel level>
    <#switch level>
        <#case "D1"><#return "Junior" />
        <#case "D2"><#return "Middle" />
        <#case "D3"><#return "Senior" />
    </#switch>
    <#return level />
</#function>