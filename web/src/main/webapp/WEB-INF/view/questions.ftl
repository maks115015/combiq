<#import "templates.ftl" as templates />

<#assign head>

</#assign>

<#assign sidebar>
    <h4 class="co-sidebar-title">Популярное</h4>
    <ul class="co-popularTags">
        <#list popularTags as tag>
            <li>
                <co-tag tag="${tag.value}" count="${tag.docCount}">${tag.value}</co-tag>
            </li>
        </#list>
    </ul>
</#assign>

<@templates.layoutWithSidebar head=head dsl=dsl sidebar=sidebar>
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