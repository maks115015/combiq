<#-- @ftlvariable name="question" type="ru.atott.combiq.service.bean.Question" -->
<#-- @ftlvariable name="comment" type="ru.atott.combiq.dao.entity.QuestionComment" -->

<#import "templates.ftl" as templates />

<#assign head>
    ${templates.import("/static/bower_components/paper-input/paper-autogrow-textarea.html")}
    <link rel="canonical" href="http://combiq.ru/questions/${question.id}" />
</#assign>

<@templates.layoutWithoutSidebar head=head dsl=dsl chapter='questions'>
    <div class="row">
        <div class="col-md-9">
            <div class="co-question">
                <div class="co-flex">
                    <div>
                        <div class="co-question-title">
                        ${question.title}
                        </div>

                        <div class="co-question-body">
                            <@templates.contentEditor content=question.body url='/questions/${question.id}/content' />
                        </div>

                        <@questionStaff />
                    </div>
                </div>
            </div>

            <@questionPosition />

            <@questionComments />
        </div>
        <div class="col-md-3 co-question-aside">
            <div>
                <h4>Полезное</h4>
                <ol class="list-unstyled co-question-aside-tips">
                    <#list tags as tag>
                        <#if tag.suggestViewOthersQuestionsLabel??>
                            <li>
                                <div class="row">
                                    <div class="col-md-1">
                                        <span class="glyphicon glyphicon-link" aria-hidden="true"></span>
                                    </div>
                                    <div class="col-md-10">
                                        <a href="/questions/tagged/${tag.tag?url}">
                                            ${tag.suggestViewOthersQuestionsLabel?html} →
                                        </a>
                                    </div>
                                </div>
                            </li>
                        </#if>
                    </#list>

                    <li>
                        <div class="row">
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-ok"></span>
                            </div>
                            <div class="col-md-10">
                                Возможно, вам будет проще готовиться к собеседованию по уже готовым
                                <a href="/questionnaires">опросникам →</a>
                            </div>
                        </div>
                    </li>
                </ol>
            </div>
        </div>
    </div>
</@templates.layoutWithoutSidebar>

<#macro questionStaff>
    <ul class="co-question-staff">
        <#if question.level??>
            <li class="co-question-staff-level">
                Уровень <strong><a href="/questions/level/${question.level}">${question.level}</a></strong>
            </li>
        </#if>
        <#list question.tags as tag>
            <li>
                <co-tag tag="${tag}">${tag}</co-tag>
            </li>
        </#list>
    </ul>
</#macro>

<#macro questionPosition>
    <#if position??>
        <div>
            <ul class="co-question-position">
                <#if position.previosQuestionId??>
                    <li>
                        <a href="/questions/${position.previosQuestionId}?index=${position.index - 1}&dsl=${dsl!''?url}">
                            <span class="co-arrow">←</span> предыдущий
                        </a>
                    </li>
                </#if>
                <li>
                    <strong>${position.index + 1}</strong> из <a href="/questions/search?q=${dsl!''?url}">${position.total}</a>
                </li>
                <#if position.nextQuestionId??>
                    <li>
                        <a href="/questions/${position.nextQuestionId}?index=${position.index + 1}&dsl=${dsl!''?url}">
                            следующий <span class="co-arrow">→</span>
                        </a>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="clearfix"></div>
    </#if>
</#macro>

<#macro questionComments>
    <#--<div class="co-my-question-comment co-flex">
        <div class="co-my-question-comment-label">
            <div>
                Ваш комментарий к вопросу, <br/>
                его видите только Вы
            </div>
        </div>
        <div class="co-my-question-comment-box">
            <paper-autogrow-textarea rows="3" >
                <textarea id="questionMyComment" ${templates.if(!user??, 'disabled')}>${questionComment()}</textarea>
            </paper-autogrow-textarea>
            <paper-button onclick="saveQuestionComment(document.getElementById('questionMyComment').value, '${question.id}');" ${templates.if(!user??, 'disabled')}>Сохранить</paper-button>
            <span id="questionMyCommentStatus"></span>
        </div>
    </div>-->

    <div>
        <h4>Комментарии</h4>
        <co-commentposter params="questionId: '${question.id?js_string}'"></co-commentposter>
        <div style="margin-top: 25px;">
            <#if comments?? && comments?size &gt; 0>
                <ul class="co-comments">
                    <#list comments as comment>
                        <li>
                            <span class="co-comments-meta">${comment.userName}, ${comment.postDate?string('dd MMMM yyyy, hh:mm')}</span>
                            <div class="co-comments-body">
                            ${comment.content.html}
                            </div>
                        </li>
                    </#list>
                </ul>
            <#else>
                Комментариев пока нет.
            </#if>
        </div>
    </div>
</#macro>

<#function questionComment>
    <#if !user??>
        <#return 'Например, Вы можете сохранить здесь короткий ответ или ссылку.\nТолько зарегистрированные пользователи могут сохранять комментарии.' />
    </#if>
    <#if user?? && question.attrs?? && question.attrs.comment??>
        <#return question.attrs.comment! />
    </#if>
    <#return '' />
</#function>

<#function questionReputationVotedUp>
    <#if user?? && question.attrs?? && question.attrs.reputation?? && question.attrs.reputation &gt; 0>
        <#return true />
    </#if>
    <#return false />
</#function>

<#function questionReputationVotedDown>
    <#if user?? && question.attrs?? && question.attrs.reputation?? && question.attrs.reputation &lt; 0>
        <#return true />
    </#if>
    <#return false />
</#function>