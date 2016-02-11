<#import "../_layout/templates.ftl" as templates />
<#import "../_layout/functions.ftl" as f />
<#import "admin-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='posts' />
</#assign>

<@templates.layoutWithSidebar
        chapter='admin'
        pageTitle='Редактировать статью'
        subTitle='Редактировать статью из раздела Блог'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <#-- @ftlvariable name="post" type="ru.atott.combiq.service.bean.Post" -->

    <co-posteditor params="
        title: '${(post.title)!?js_string}',
        content: '${(post.content.markdown)!?js_string}',
        postId: ${f.if(post??, "'" + (post.id)!?js_string + "'", "null")},
        published: ${((post.published)!false)?c}
    ">
    </co-posteditor>

</@templates.layoutWithSidebar>