<#import "templates.ftl" as templates />

<#assign head>
    ${templates.import("/static/bower_components/paper-input/paper-input-decorator.html")}
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

<@templates.layoutWithSidebar head=head dsl=dsl sidebar=sidebar chapter='questions'>
    <form action="/questions/search" method="get" id="searchForm">
        <div class="co-search">
            <paper-input-decorator value="${dsl!}" label="Поисковый запрос">
                <div class="co-flex">
                    <input autocomplete="off" type="text" name="q" value="${dsl!}"/>
                    <paper-button onclick="document.getElementById('searchForm').submit();">
                        Искать
                    </paper-button>
                </div>
            </paper-input-decorator>
        </div>
        <div class="co-search-help-tip">
            <a href="https://github.com/atott/combiq/wiki/%D0%9F%D0%BE%D0%B8%D1%81%D0%BA">
                Вы можете задавать гибкие условия поиска, например, по тэгам или уровню <span class="co-arrow">→</span>
            </a>
        </div>
    </form>
    <ul class="co-questions">
        <#list questions as question>
            <li>
                <co-question reputation="${question.reputation}" level="${question.level}" tags="${question.tags?join(',')}">
                    <a href="/questions/${question.id}?index=${paging.from + question_index}&dsl=${dsl?url}">${question.title}</a>
                </co-question>
            </li>
        </#list>
    </ul>
    <@templates.paging paging=paging />
</@templates.layoutWithSidebar>