<#-- @ftlvariable name="question" type="ru.atott.combiq.service.bean.Question" -->
<#-- @ftlvariable name="comment" type="ru.atott.combiq.dao.entity.QuestionComment" -->

<#import "_layout/templates.ftl" as templates />
<#import "_layout/parts.ftl" as parts />
<#import "_layout/functions.ftl" as functions />

<#assign head>

</#assign>

<#assign sidebar>

</#assign>

<@templates.layoutWithSidebar
        head=head
        chapter='blog'
        pageTitle=post.title
        sidebar=sidebar
        ogDescription=post.title>



</@templates.layoutWithSidebar>