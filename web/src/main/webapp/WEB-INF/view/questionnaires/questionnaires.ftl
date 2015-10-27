<#import "../templates.ftl" as templates />
<#import "questionnaires-common.ftl" as common />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@security.authorize access="hasRole('sa')" var="allowedEditQuestionnaireTitle" />
<@security.authorize access="hasRole('sa')" var="allowedEditPageContent" />

<#assign head>
    <#if allowedEditQuestionnaireTitle>
        ${templates.import("/static/elements/co-markdown/co-markdown.html")}
    </#if>
</#assign>

<#assign sidebar>
    <@common.sidebar activeMenuItem='questionnaires' />
</#assign>

<@templates.layoutWithSidebar head=head
        chapter='questionnaires'
        subTitle='Пройти собеседование'
        pageTitle='Пройти собеседование'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>
    <@templates.headBanners></@templates.headBanners>
    <#if allowedEditPageContent>
        <co-markdown
                class="js-page-content"
                value="${(questionnairesPageContent.markdown)!?html}"
                preview="${(questionnairesPageContent.html)!?html}">
        </co-markdown>
    <#else>
        ${(questionnairesPageContent.html)!''}
    </#if>
    <ul>
        <#list questionnaires as questionnaire>
            <li>
                <a href="/questionnaire/${questionnaire.id}">
                    ${questionnaire.name}
                </a>
                <#if allowedEditQuestionnaireTitle>
                    <co-markdown
                            class="js-questionnaire-title"
                            data-questionnaire-id="${questionnaire.id}"
                            value="${(questionnaire.title.markdown)!?html}"
                            preview="${(questionnaire.title.html)!?html}">
                    </co-markdown>
                <#else>
                    <#if questionnaire.title??>
                        <div class="co-questionnaire-title">
                            ${questionnaire.title.html}
                        </div>
                    </#if>
                </#if>
            </li>
        </#list>
    </ul>
    <script>
        $('co-markdown.js-questionnaire-title').on('apply', function(e) {
            var value = this.value;
            var questionnaireId = $(this).data('questionnaire-id');

            $.ajax({
                url: '/questionnaire/' + questionnaireId + '/title',
                data: JSON.stringify({content: value}),
                contentType: 'application/json',
                method: 'POST',
                success: function(result) { }
            });
        });
        $('co-markdown.js-page-content').on('apply', function(e) {
            var value = this.value;

            $.ajax({
                url: '/content/questionnaires-page',
                data: JSON.stringify({content: value}),
                contentType: 'application/json',
                method: 'POST',
                success: function(result) { }
            });
        });
    </script>
</@templates.layoutWithSidebar>