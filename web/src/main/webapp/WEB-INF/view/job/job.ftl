<#import "../_layout/templates.ftl" as templates />
<#import "../_layout/parts.ftl" as parts />
<#import "job-common.ftl" as common />

<#assign sidebar>
    <co-jobsubscribe></co-jobsubscribe>

    <@common.sidebar activeMenuItem='job' />
</#assign>

<@templates.layoutWithSidebar
        chapter='job'
        subTitle='Предложения о работе на позицию Java разработчик'
        pageTitle='Работа для Java разработчиков'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <@parts.contentEditor content=jobPageContent />

</@templates.layoutWithSidebar>