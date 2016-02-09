<#import "_layout/templates.ftl" as templates />

<@templates.layoutBody head=head chapter='questionnaires' subTitle='опросник ' + questionnaire.name>
    <div class="container">
        <h1>Опросник ${questionnaire.name}</h1>
        <div class="row">
            <div class="col-md-9">
                <table class="co-questionnaire-table">
                    <thead>
                    <tr>
                        <td class="text-center">№</td>
                        <td style="width: 95%">Вопрос</td>
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
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
            <div class="col-md-3 text-center">
                <div data-spy="affix" class="co-affix" data-offset-top="250">
                    <#assign downloadUrl="/questionnaire/" + questionnaire.id + "/Опросник " + questionnaire.name?url + ".pdf" />
                    <!--noindex-->
                    <a href="${downloadUrl}"
                       class="co-asideimage-container"
                       title="Скачать опросник ${questionnaire.name?html}"
                       rel="nofollow">
                        <img src="/static/images/download_questionnaire.png" alt="Скачать опросник, PDF формат"><br>
                    </a>
                    <p>
                        <a href="${downloadUrl}">Скачайте</a> опросник <em>${questionnaire.name?html}</em>, распечатайте его
                        и возьмите с собой на собеседование.
                    </p>
                    <!--/noindex-->
                </div>
            </div>
        </div>
    </div>
</@templates.layoutBody>