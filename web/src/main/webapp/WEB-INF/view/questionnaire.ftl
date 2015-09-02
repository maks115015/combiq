<#import "templates.ftl" as templates />

<@templates.layoutBody head=head>
    <div class="container">
        <h1>Опросник ${questionnaire.name}</h1>
        <p>
            <a class="co-action" href="#" onclick="window.print(); return false;">Распечатайте</a> этот опросник и возьмите с собой на собеседование.
        </p>
        <table class="co-questionnaire-table">
            <thead>
            <tr>
                <td class="text-center">№</td>
                <td style="width: 60%">Вопрос</td>
                <td>Оценка</td>
                <td style="width: 30%">Комментарий</td>
            </tr>
            </thead>
            <tbody>
                <#list questionnaire.questions as question>
                <tr>
                    <td class="text-center">
                        ${question_index + 1}
                    </td>
                    <td>
                        <a href="/questions/${question.id}">${question.title}</a>
                        <#if question.tip??>
                            <div class="co-question-tip">
                                <span class="co-question-tip-title">Подсказка:</span> ${question.tip!}
                            </div>
                        </#if>
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</@templates.layoutBody>