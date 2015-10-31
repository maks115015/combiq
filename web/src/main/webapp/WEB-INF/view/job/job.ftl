<#import "../templates.ftl" as templates />
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

    <@templates.contentEditor content=jobPageContent />

</@templates.layoutWithSidebar>