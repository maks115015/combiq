<#import "stats.ftl" as stats />

<#macro layoutHtml head='' dsl=''>
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


        <link rel="import" href="/static/bower_components/paper-button/paper-button.html?v=${resourceVersion}">
        <link rel="import" href="/static/bower_components/paper-dialog/paper-action-dialog.html?v=${resourceVersion}">
        <link rel="import" href="/static/bower_components/core-toolbar/core-toolbar.html?v=${resourceVersion}">
        <link rel="import" href="/static/bower_components/paper-icon-button/paper-icon-button.html?v=${resourceVersion}">
        <link rel="import" href="/static/elements/co-question.html?v=${resourceVersion}">
        <link rel="import" href="/static/elements/co-tag.html?v=${resourceVersion}">
        ${head}
    </head>
    <body>
        <@stats.metrika />
        <nav class="navbar navbar-default navbar-static-top co-header">
            <ul class="co-topmenu">
                <li>
                    <a class="co-topmenu-mainer" href="/" title="combiq.ru">
                        <img src="/static/images/site/logo.png" />
                    </a>
                </li>
                <li>
                    <iframe style="margin-bottom: -7px;" src="https://ghbtns.com/github-btn.html?user=atott&repo=combiq&type=star&count=true&size=large" frameborder="0" scrolling="0" width="160px" height="30px"></iframe>
                </li>
                <li>
                    <a href="https://github.com/atott/combiq/wiki">о проекте</a>
                </li>
                <li>
                    <a class="co-out" href="https://github.com/atott/combiq">на Github.com</a>
                </li>
                <#if user??>
                    <li class="co-authcode">
                        <strong>${user.login}</strong><a href="/logout.do">.logout()</a><span>;</span>
                    </li>
                <#else>
                    <li class="co-authcode">
                        <strong>anonymous</strong><a href="/login.do">.login()</a><span>;</span>
                    </li>
                </#if>
                <li class="co-searchbox">
                    <form action="/questions/search" method="get">
                        <div class="input-group">
                            <input name="q" type="text" class="form-control" value="${dsl!}">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="submit">
                                    <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                                </button>
                            </span>
                        </div>
                    </form>
                </li>
                <li class="co-getstarted">
                    <a href="/questions">Вопросы</a>
                </li>
            </ul>
        </nav>
        <#nested />
        <footer>
            <div class="container co-footer">
                <div class="co-inline">
                    <div>
                        <strong>Combiq.ru, 2014-2015</strong>
                    </div>
                    <div>
                        <a href="https://github.com/atott/combiq">https://github.com/atott/combiq</a>
                    </div>
                    <div>
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
        <#if !user??>
            <@inviteAuth />
        </#if>
    </body>
</html>
</#macro>

<#macro layoutWithSidebar head='' dsl='' sidebar=''>
    <@layoutHtml head=head dsl=dsl>
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

<#macro layoutWithoutSidebar head='' dsl=''>
    <@layoutHtml head=head dsl=dsl>
    <div class="container">
        <#nested />
    </div>
    </@layoutHtml>
</#macro>

<#macro layoutBody head='' dsl=''>
    <@layoutHtml head=head dsl=dsl>
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