<#import "../_layout/templates.ftl" as templates />
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

    <@templates.contentEditor content=questionnairesPageContent></@templates.contentEditor>

    <ul>
        <#list questionnaires as questionnaire>
            <li>
                <a href="/questionnaire/${questionnaire.id}">
                    ${questionnaire.name}
                </a>
                <@templates.contentEditor
                    content=questionnaire.title
                    url='/questionnaire/' + questionnaire.id + '/title'>
                </@templates.contentEditor>
            </li>
        </#list>
    </ul>

    <@templates.contentEditor content=questionnairesPageBottomContent></@templates.contentEditor>
</@templates.layoutWithSidebar>