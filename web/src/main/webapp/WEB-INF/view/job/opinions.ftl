<#import "../templates.ftl" as templates />
<#import "job-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='opinions' />
</#assign>

<@templates.layoutWithSidebar
        chapter='job'
        subTitle='Обсуждение компаний, вакансий, условий труда'
        pageTitle='Мнения'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <@templates.contentEditor content=jobOpinionsPageContent />

</@templates.layoutWithSidebar>