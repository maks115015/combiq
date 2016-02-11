<#import "../_layout/templates.ftl" as templates />
<#import "admin-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='posts' />
</#assign>

<@templates.layoutWithSidebar
        chapter='admin'
        pageTitle='Редактировать статью'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <#-- @ftlvariable name="post" type="ru.atott.combiq.service.bean.Post" -->

    <co-posteditor></co-posteditor>

</@templates.layoutWithSidebar>