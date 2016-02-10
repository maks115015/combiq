<#-- @ftlvariable name="question" type="ru.atott.combiq.service.bean.Question" -->
<#-- @ftlvariable name="comment" type="ru.atott.combiq.dao.entity.QuestionComment" -->

<#import "templates.ftl" as templates />

<#assign head>
    <script type="text/javascript" src="http://vk.com/js/api/share.js?93" charset="windows-1251"></script>
<<<<<<< HEAD

    <link rel="canonical" href="${canonicalUrl}" />
=======
    <#--${templates.import("/static/bower_components/paper-input/paper-autogrow-textarea.html")}-->
    <#--<link rel="canonical" href="${canonicalUrl}" />-->
>>>>>>> refs/remotes/origin/master
</#assign>

<#assign sidebar>
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

    <#if question.classNames?? && question.classNames?size != 0>
        <div>
            <h4>JavaDoc</h4>
            <ol class="list-unstyled co-question-aside-tips">
                <#list question.classNames as className>
                    <li>
                        <div class="row">
                            <div class="col-md-1">
                                <span class="glyphicon glyphicon-book"></span>
                            </div>
                            <div class="col-md-10">
                                <a href="https://docs.oracle.com/javase/8/docs/api/${className?replace(".", "/")}.html">${className}</a>
                                <span style="font-size: 12px">
                                    (<a style="color: #777777"
                                        href="http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/${className?replace(".", "/")}.java">src</a>)
                                </span>
                            </div>
                        </div>
                    </li>
                </#list>
            </ol>
        </div>
    </#if>

    <#if landing>
        <div>
            <h4>ВКонтакте</h4>
            <!-- VK Widget -->
            <div id="vk_groups"></div>
            <script type="text/javascript">
                VK.Widgets.Group("vk_groups", {mode: 2, width: "220", height: "400"}, 111268840);
            </script>
        </div>
    </#if>
</#assign>

<@templates.layoutWithSidebar
        head=head
        dsl=dsl
        chapter='questions'
        subTitle=question.title
        sidebar=sidebar
        sidebarContainerClass='co-question-aside'
        ogDescription=question.title>

        <div class="co-question">
            <div>
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

        <#if landing>
            <@landingBlock />
        </#if>

        <@questionComments />
</@templates.layoutWithSidebar>

<#macro landingBlock>
    <div class="co-landing">
        <div class="row">
            <div class="col-md-8 co-landing-another-questions">
                <#if anotherQuestions??>
                    <h4>Другие вопросы для подготовки к собеседованию</h4>
                    <ul>
                        <#list anotherQuestions as anotherQuestion>
                            <li>
                                <a href="/questions/${anotherQuestion.id}">
                                    ${anotherQuestion.title}
                                </a>
                            </li>
                        </#list>
                    </ul>
                </#if>
            </div>
            <div class="col-md-4 co-landing-another-desc">
                <a href="http://combiq.ru">Combiq.ru</a> - это проект с открытым исходным кодом, цель которого
                собрать в одном месте
                всю полезную информацию для Java программистов, которые
                готовятся к собеседованию на новое место работы.
            </div>
        </div>
    </div>
</#macro>

<#macro questionStaff>
    <ul class="co-question-staff">
        <#list question.tags as tag>
            <li class="co-small">
                <a class="co-tag" href="/questions/tagged/${tag}">${tag}</a>
            </li>
        </#list>
        <#if question.level??>
            <li style="margin-left: 15px;" class="co-small">
                Уровень <a href="/questions/level/${question.level}">${templates.explainLevel(question.level)}</a>
            </li>
        </#if>
        <li class="pull-right" style="padding-top: 2px;">
            <script type="text/javascript"><!--
            document.write(VK.Share.button({url: "${canonicalUrl}"},{type: "round", text: "Поделиться", eng: 1}));
            --></script>
        </li>
    </ul>
</#macro>

<#macro questionPosition>
    <#if position??>
        <div >
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
        <div style="margin-bottom: 25px;" class="clearfix"></div>
    </#if>
</#macro>

<#macro questionComments>
    <div>
        <h4>Комментарии</h4>
        <co-commentposter params="questionId: '${question.id?js_string}'"></co-commentposter>
        <div style="margin-top: 25px;">
            <#if comments?? && comments?size &gt; 0>
                <ul class="co-comments">
                    <#list comments as comment>
                        <li>
                            <span class="co-comments-meta" id="comment-${comment.id!}">
                                ${comment.userName}, ${comment.postDate?string('dd MMMM yyyy, hh:mm')}
                                <#if comment.editDate??>
                                    <span class="co-comments-meta-edited" title="${comment.editUserName!comment.userName}, ${comment.editDate?string('dd MMMM yyyy, hh:mm')}">изменён</span>
                                </#if>
                                <#if (user.id)! == comment.userId
                                        || templates.hasRole('sa')
                                        || templates.hasRole('contenter') >
                                    <a class="pull-right" href="#"
                                        onclick="ko.openDialog('co-editcomment', {
                                            questionId: '${question.id?js_string}',
                                            commentId: '${comment.id?js_string}',
                                            commentMarkdown: '${comment.content.markdown?js_string}'
                                        }); return false;">
                                        Изменить
                                    </a>
                                </#if>
                            </span>
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