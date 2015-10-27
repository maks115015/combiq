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
    <@common.sidebar activeMenuItem='interview' />
</#assign>

<@templates.layoutWithSidebar head=head
        chapter='questionnaires'
        subTitle='Провести собеседование'
        pageTitle='Провести собеседование'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>
    <@templates.headBanners></@templates.headBanners>
    <#if allowedEditPageContent>
        <co-markdown
                class="js-page-content"
                value="${(interviewPageContent.markdown)!?html}"
                preview="${(interviewPageContent.html)!?html}">
        </co-markdown>
    <#else>
        ${(interviewPageContent.html)!''}
    </#if>
    <script>
        $('co-markdown.js-page-content').on('apply', function(e) {
            var value = this.value;

            $.ajax({
                url: '/content/interview-page',
                data: JSON.stringify({content: value}),
                contentType: 'application/json',
                method: 'POST',
                success: function(result) { }
            });
        });
    </script>
</@templates.layoutWithSidebar>