<#import "../_layout/templates.ftl" as templates />
<#import "../_layout/parts.ftl" as parts />
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

    <@parts.contentEditor content=jobOpinionsPageContent />

</@templates.layoutWithSidebar>