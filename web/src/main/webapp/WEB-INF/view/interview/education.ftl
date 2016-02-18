<#import "../_layout/templates.ftl" as templates />
<#import "../_layout/parts.ftl" as parts />
<#import "interview-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='education' />
</#assign>

<@templates.layoutWithSidebar
        chapter='interview'
        subTitle='План подготовки к собеседованию на позицию Java разработчик уровня D2-D3'
        pageTitle='Подготовка Java Dev D2-D3'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <@parts.contentEditor content=educationPageContent></@parts.contentEditor>

</@templates.layoutWithSidebar>