<#import "templates.ftl" as templates />

<#assign head>
    <link rel="import" href="/static/elements/co-question.html">
    <link rel="stylesheet" href="/static/font/comfortaa/comfortaa.css">
</#assign>

<@templates.clear head=head>
    <div class="co-mainer">
        <div class="text-center">
            <img src="/static/images/promo/main.png">
            <div class="co-mainer-title">
                Искать и повторять вопросы к собеседованию Java <br>
                только здесь, не сметь уходить!
            </div>
        </div>
    </div>
    <div class="container">
        <ul>
            <#list questions as question>
                <li>
                    <co-question>
                    ${question.title}
                    </co-question>
                </li>
            </#list>
        </ul>
    </div>
</@templates.clear>