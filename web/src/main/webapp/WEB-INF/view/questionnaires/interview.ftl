<#import "../templates.ftl" as templates />
<#import "questionnaires-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='interview' />
</#assign>

<@templates.layoutWithSidebar
        chapter='questionnaires'
        subTitle='Советы как провести собеседование на позицию Java разработчик'
        pageTitle='Провести собеседование'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <@templates.headBanners></@templates.headBanners>

    <@templates.contentEditor content=interviewPageContent></@templates.contentEditor>

</@templates.layoutWithSidebar>