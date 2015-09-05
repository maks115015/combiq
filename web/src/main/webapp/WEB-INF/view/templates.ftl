<#import "stats.ftl" as stats />

<#macro layoutHtml head='' dsl='' chapter='' showFooter=true>
<!DOCTYPE html>
<!--    Дорогой друг!
        Злой красный человек спалил тебя ;)
        Теперь помоги ему сделать проект лучше -
        сделай свой вклад в проект Combiq.ru,
        https://github.com/atott/combiq
-->
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name='yandex-verification' content='526ff629012169b9' />

        <title>Combiq - вопросы для собеседования Java</title>

        <link href='http://fonts.googleapis.com/css?family=Roboto+Slab:400,300&subset=latin,cyrillic' rel='stylesheet' type='text/css'>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="/static/css/styles.css?v=${resourceVersion}">
        <link rel="stylesheet" href="/static/font/roboto/roboto.css">
        <link rel="stylesheet" href="/static/font/comfortaa/comfortaa.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="/static/bower_components/webcomponentsjs/webcomponents.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.17/require.min.js"></script>
        <script src="/static/js/site.js?v=${resourceVersion}"></script>

        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <![endif]-->
        <script>
            requirejs.config({
                baseUrl: '/static/js',
                paths: {
                    text: 'lib/text'
                }
            });
            window.co = {
                userId: ${if(userId??, '"' + (userId!'') + '"', 'null')}
            };
        </script>


        ${import("/static/bower_components/paper-button/paper-button.html")}
        ${import("/static/bower_components/paper-dialog/paper-action-dialog.html")}
        ${import("/static/bower_components/core-toolbar/core-toolbar.html")}
        ${import("/static/bower_components/paper-icon-button/paper-icon-button.html")}
        ${import("/static/elements/co-question/co-question.html")}
        ${import("/static/elements/co-tag/co-tag.html")}
        ${head}
    </head>
    <body>
        <#if env == 'prod'>
            <@stats.metrika />
            <@stats.ga />
            <@stats.gtm />
        </#if>
        <div class="container">
            <nav class="navbar navbar-default navbar-static-top co-header">
                <ul class="co-topmenu">
                    <li>
                        <a class="co-topmenu-mainer" href="/" title="combiq.ru">
                            <img src="/static/images/site/flat-logo-64.png" />
                            <span>Combiq.ru</span>
                        </a>
                    </li>
                </ul>
                <ul class="co-topmenu pull-right">
                    <li class="co-chapter ${if(chapter == 'questions', 'active')}">
                        <a href="/questions">Вопросы</a>
                    </li>
                    <li class="co-chapter ${if(chapter == 'questionnaires', 'active')}">
                        <a href="/questionnaires">Опросники</a>
                    </li>
                    <li class="co-chapter ${if(chapter == 'education', 'active')}">
                        <a href="/education">План подготовки</a>
                    </li>
                    <#if user??>
                        <li class="co-auth">
                            <#if user.headAvatarUrl??>
                                <img src="${user.headAvatarUrl!}">
                            </#if>
                            <a href="/logout.do">Выйти</a>
                        </li>
                    <#else>
                        <li>
                            <a href="/login.do">Войти</a>
                        </li>
                    </#if>
                </ul>
            </nav>
        </div>
        <#nested />
        <#if showFooter>
            <footer>
                <div class="container">
                    <div class="co-inline">
                        <div>
                            <strong>Combiq.ru, 2014-2015</strong>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="https://github.com/atott/combiq">https://github.com/atott/combiq</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="/donate">Поддержать проект</a>
                        </div>
                    </div>
                    <span class="co-informer">
                        <#if env == 'prod'>
                            <@stats.informer/>
                        </#if>
                    </span>
                </div>
            </footer>
        </#if>
        <#if !user??>
            <@inviteAuth />
        </#if>
    </body>
</html>
</#macro>

<#macro layoutWithSidebar head='' dsl='' sidebar='' chapter=''>
    <@layoutHtml head=head dsl=dsl chapter=chapter>
        <div class="container">
            <div class="col-md-9">
                <#nested />
            </div>
            <div class="col-md-3">
                ${sidebar}
            </div>
        </div>
    </@layoutHtml>
</#macro>

<#macro layoutWithoutSidebar head='' dsl='' chapter=''>
    <@layoutHtml head=head dsl=dsl chapter=chapter>
    <div class="container">
        <#nested />
    </div>
    </@layoutHtml>
</#macro>

<#macro layoutBody head='' dsl='' chapter='' showFooter=true>
    <@layoutHtml head=head dsl=dsl chapter=chapter showFooter=showFooter>
        <#nested />
    </@layoutHtml>
</#macro>

<#macro inviteAuth>
    <div>
        <paper-action-dialog heading="Войдите на сайт" layered="true" backdrop="true" id="inviteAuthDialog">
            <p>
                Для того, чтобы иметь возможность голосовать за вопросы, нужно <a href="/login.do">войти</a> на сайт
                <strong>combiq.ru</strong>.
            </p>
            <paper-button affirmative>Не сейчас</paper-button>
            <paper-button onclick="location.href = '/login.do';" class="co-ok-button" affirmative autofocus>Войти</paper-button>
        </paper-action-dialog>
    </div>
</#macro>

<#function if condition a b=''>
    <#if condition>
        <#return a>
    <#else>
        <#return b>
    </#if>
</#function>

<#function import href>
    <#return '<link rel="import" href="' + href + '?v=$' + resourceVersion + '">' />
</#function>

<#function importElement name>
    <#return '<link rel="import" href="/static/build/elements/' + name + '.html?v=' + resourceVersion + '">' />
</#function>

<#macro paging paging>
    <nav>
        <ul class="pagination">
            <#list paging.pages as page>
                <#if page.omission>
                    <li>
                        <span>...</span>
                    </li>
                <#else>
                    <li class="${if(page.active, 'active')}">
                        <a href="${page.url!''}">${page.title}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </nav>
</#macro>