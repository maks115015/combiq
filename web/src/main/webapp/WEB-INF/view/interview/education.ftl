<#import "../_layout/templates.ftl" as templates />
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

    <@templates.contentEditor content=educationPageContent></@templates.contentEditor>

</@templates.layoutWithSidebar>