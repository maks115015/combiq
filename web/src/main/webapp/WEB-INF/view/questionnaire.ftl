<#import "templates.ftl" as templates />

<@templates.layoutBody head=head>
    <div class="container">
        <h1>Опросник ${questionnaire.name}</h1>
        <ol>
            <#list questionnaire.questions as question>
                <li>
                    <a href="/questions/${question.id}">${question.title}</a>
                </li>
            </#list>
        </ol>
    </div>
</@templates.layoutBody>