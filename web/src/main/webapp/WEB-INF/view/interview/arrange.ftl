<#import "../templates.ftl" as templates />
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

    <@templates.headBanners></@templates.headBanners>

    <@templates.contentEditor content=interviewPageContent></@templates.contentEditor>

</@templates.layoutWithSidebar>