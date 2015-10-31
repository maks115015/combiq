<#import "../templates.ftl" as templates />
<#import "questionnaires-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='questionnaires' />
</#assign>

<@templates.layoutWithSidebar
        chapter='questionnaires'
        subTitle='Опросники для подготовки к собеседованию на позицию Java разработчик'
        pageTitle='Пройти собеседование'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <@templates.headBanners></@templates.headBanners>

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