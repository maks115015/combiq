<#import "templates.ftl" as templates />

<#assign head>
    <link rel="import" href="/static/elements/co-question.html">
</#assign>

<@templates.layoutWithSidebar head=head>
    <ul class="co-questions">
        <#list questions as question>
            <li>
                <co-question tags="${question.tags?join(',')}">
                    ${question.title}
                </co-question>
            </li>
        </#list>
    </ul>
    <@templates.paging paging=paging />
</@templates.layoutWithSidebar>