<#import "stats.ftl" as stats />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro layoutHtml head='' dsl='' chapter='' subTitle='' showFooter=true gtmPageName='' ogDescription='' ogTitle=''>
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

        <meta name="og:site_name" content="Combiq.ru">
        <meta name="og:description" content="${if(ogDescription!='', ogDescription, "Вопросы для подготовки к собеседованию Java кандидатам и опросники для работадателей. План подготовки к собеседованию.")}">
        <meta name="og:title" content="${if(ogTitle!='', ogTitle, "Готовьтесь к Java собеседованию на Combiq.ru")}">
        <meta name="og:image" content="/static/images/image.png">

        <title>Combiq${if(subTitle == '', '', ' - ' + subTitle)}</title>

        <link href='http://fonts.googleapis.com/css?family=Roboto+Slab:400,300&subset=latin,cyrillic' rel='stylesheet' type='text/css'>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="/static/css/styles.css?v=${resourceVersion}">
        <link rel="stylesheet" href="/static/font/roboto/roboto.css">
        <link rel="stylesheet" href="/static/font/comfortaa/comfortaa.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="/static/bower_components/webcomponentsjs/webcomponents.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.17/require.min.js"></script>
        <script src="/static/js/lib/knockout.js"></script>
        <script src="/static/js/lib/knockout.dialog.js?v=${resourceVersion}"></script>
        <script src="/static/js/site.js?v=${resourceVersion}"></script>
        <script type="text/javascript" src="//vk.com/js/api/openapi.js?117"></script>

        <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <![endif]-->
        <script>
            requirejs.config({
                baseUrl: '/static',
                paths: {
                    text: 'js/lib/text',
                    css: 'js/lib/css',
                    ajax: 'js/lib/ajax'
                }
            });
            window.co = {
                userId: ${if(userId??, '"' + (userId!'') + '"', 'null')},
                userEmail: '${userEmail!''}',
                githubClientId: '${githubClientId?js_string}',
                githubClientState: '${githubClientState?js_string}',
                vkClientId: '${vkClientId?js_string}',
                vkClientState: '${vkClientState?js_string}',
                vkCallbackUrl: '${vkCallbackUrl?js_string}'
            };
        </script>
        <script src="/static/ko_components/components.js?v=${resourceVersion}"></script>

        ${import("/static/bower_components/paper-button/paper-button.html")}
        ${import("/static/bower_components/paper-dialog/paper-action-dialog.html")}
        ${import("/static/bower_components/core-toolbar/core-toolbar.html")}
        ${import("/static/bower_components/paper-icon-button/paper-icon-button.html")}
        ${import("/static/elements/co-question/co-question.html")}
        ${import("/static/elements/co-tag/co-tag.html")}

        <@security.authorize access="hasRole('sa')" var="allowEditConent" />

        <#if allowEditConent>
        ${import("/static/elements/co-markdown/co-markdown.html")}
        </#if>

        ${head}
    </head>
    <body>
        <#if env == 'prod'>
            <@stats.metrika />
            <@stats.commonGtmInitialization gtmPageName />
            <@stats.gtm />
            <@stats.ga />
        </#if>
        <div class="container">
            <nav class="navbar navbar-default navbar-static-top co-header">
                <ul class="co-topmenu">
                    <li>
                        <img style="vertical-align: baseline; margin-right: 14px;" src="/static/images/site/flat-logo-64.png" />
                        <div class="co-inline">
                            <a class="co-topmenu-mainer" href="/" title="combiq.ru">
                                <span>Combiq.ru</span>
                            </a>
                            <br>
                            <span style="font-size: 12px;">
                                Всё, что может вам потребоваться для подготовки
                                <br>
                                к Java собеседованию
                            </span>
                        </div>
                    </li>
                </ul>
                <ul class="co-topmenu pull-right">
                    <li class="co-socials">
                        <a href="https://vk.com/combiq">
                            <img src="/static/images/social/vk.png" alt="Java Combiq on VK.com">
                        </a>
                    </li>
                    <li class="co-socials" style="padding-right: 15px;">
                        <a href="https://github.com/atott/combiq">
                            <img src="/static/images/social/github.png" alt="Combiq on Github">
                        </a>
                    </li>
                    <li class="co-chapter ${if(chapter == 'questions', 'active')}">
                        <a href="/questions">Вопросы</a>
                    </li>
                    <li class="co-chapter ${if(chapter == 'questionnaires', 'active')}">
                        <a href="/questionnaires">Собеседование</a>
                    </li>
                    <li class="co-chapter ${if(chapter == 'education', 'active')}">
                        <a href="/education">Подготовка</a>
                    </li>
                    <li class="co-chapter ${if(chapter == 'job', 'active')}">
                        <a href="/job">Работа</a>
                    </li>
                    <#if user??>
                        <li class="co-auth">
                            <#if user.headAvatarUrl??>
                                <img width="46" height="46" src="${user.headAvatarUrl!}">
                            </#if>
                            <a href="/logout.do">Выйти</a>
                        </li>
                    <#else>
                        <li class="co-auth">
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
                            <strong>Combiq.ru, 2014-2016</strong>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
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
        <script>
            ko.applyBindings({}, document.body);
        </script>
    </body>
</html>
</#macro>

<#macro headBanners>
</#macro>

<#macro layoutWithSidebar head='' dsl='' sidebar='' chapter='' subTitle='' pageTitle='' mainContainerClass=''>
    <@layoutHtml head=head dsl=dsl chapter=chapter subTitle=subTitle>
        <#if pageTitle??>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h1>${pageTitle}</h1>
                    <#if subTitle??>
                        <div class="subtitle">${subTitle!''}</div>
                    </#if>
                </div>
            </div>
        </div>
        </#if>
        <div class="container">
            <div class="row">
                <div class="col-md-9 ${mainContainerClass}">
                    <#nested />
                </div>
                <div class="col-md-3 co-sidebar">
                    ${sidebar}
                </div>
            </div>
        </div>
    </@layoutHtml>
</#macro>

<#macro layoutWithoutSidebar head='' dsl='' chapter='' ogDescription='' ogTitle='' subTitle=''>
    <@layoutHtml head=head dsl=dsl chapter=chapter ogTitle=ogTitle ogDescription=ogDescription subTitle=subTitle>
    <div class="container">
        <#nested />
    </div>
    </@layoutHtml>
</#macro>

<#macro layoutBody head='' dsl='' chapter='' showFooter=true subTitle='' gtmPageName=''>
    <@layoutHtml head=head dsl=dsl chapter=chapter showFooter=showFooter subTitle=subTitle gtmPageName=gtmPageName>
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

<#global contentEditorIncrementor=1 />

<#macro contentEditor content url=''>
    <#-- @ftlvariable name="content" type="ru.atott.combiq.dao.entity.MarkdownContent" -->

    <@security.authorize access="hasRole('sa')" var="allowEditConent" />
    <#if allowEditConent>
        <co-markdown
                id="contentEditor${contentEditorIncrementor}"
                value="${(content.markdown)!?html}"
                preview="${(content.html)!?html}">
        </co-markdown>
        <script>
            $('#contentEditor${contentEditorIncrementor}').on('apply', function(e) {
                var value = this.value;

                $.ajax({
                    url: '${if(url == '', "/content/" + content.id!, url)}',
                    data: JSON.stringify({content: value}),
                    contentType: 'application/json',
                    method: 'POST',
                    success: function(result) { }
                });
            });
        </script>
    <#else>
        ${(content.html)!''}
    </#if>

    <#global contentEditorIncrementor = contentEditorIncrementor + 1 />
</#macro>

<#function explainLevel level>
    <#switch level>
        <#case "D1"><#return "D1 - Junior" />
        <#case "D2"><#return "D2 - Middle" />
        <#case "D3"><#return "D3 - Senior" />
    </#switch>
    <#return level />
</#function>