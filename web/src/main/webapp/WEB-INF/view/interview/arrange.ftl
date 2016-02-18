<#import "../_layout/templates.ftl" as templates />
<#import "../_layout/parts.ftl" as parts />
<#import "interview-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='arrange' />
</#assign>

<@templates.layoutWithSidebar
        chapter='interview'
        subTitle='Советы как провести собеседование на позицию Java разработчик'
        pageTitle='Провести собеседование'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <@parts.contentEditor content=interviewPageContent></@parts.contentEditor>

</@templates.layoutWithSidebar>