<#import "../_layout/templates.ftl" as templates />
<#import "../_layout/parts.ftl" as parts />
<#import "interview-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='interview' />
</#assign>

<@templates.layoutWithSidebar
        chapter='interview'
        subTitle='Опросники для подготовки к собеседованию на позицию Java разработчик'
        pageTitle='Пройти собеседование'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <@parts.contentEditor content=questionnairesPageContent></@parts.contentEditor>

    <ul>
        <#list questionnaires as questionnaire>
            <li>
                <a href="/questionnaire/${questionnaire.id}">
                    ${questionnaire.name}
                </a>
                <@parts.contentEditor
                    content=questionnaire.title
                    url='/questionnaire/' + questionnaire.id + '/title'>
                </@parts.contentEditor>
            </li>
        </#list>
    </ul>

    <@parts.contentEditor content=questionnairesPageBottomContent></@parts.contentEditor>
</@templates.layoutWithSidebar>