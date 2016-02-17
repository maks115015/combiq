<#import "stats.ftl" as stats />
<#import "banners.ftl" as banners />
<#import "menu.ftl" as menu />

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro layoutHtml head='' dsl='' chapter='' subTitle='' showFooter=true showHeader=true gtmPageName='' ogDescription='' ogTitle=''
            bodyClass=''>
<!DOCTYPE html>
<!--    Дорогой друг!
        Мы спалили тебя ;)
        Теперь помоги нам сделать проект лучше -
        сделай свой вклад в проект Combiq.ru,
        https://github.com/combiq/combiq
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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.17/require.min.js"></script>
        <script src="/static/js/lib/knockout.js"></script>
        <script src="/static/js/lib/knockout.dialog.js?v=${resourceVersion}"></script>
        <script src="/static/js/lib/knockout.bindings.js?v=${resourceVersion}"></script>
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
                vkCallbackUrl: '${vkCallbackUrl?js_string}',
                stackexchangeClientId: '${stackexchangeClientId?js_string}',
                stackexchangeClientState: '${stackexchangeClientState?js_string}',
                stackexchangeCallbackUrl: '${stackexchangeCallbackUrl?js_string}'
            };
        </script>
        <script src="/static/ko_components/components.js?v=${resourceVersion}"></script>
        <@security.authorize access="hasRole('sa')" var="allowEditConent" />
        ${head}
    </head>
    <body class="${bodyClass}">
        <#if env == 'prod'>
            <@stats.metrika />
            <@stats.commonGtmInitialization gtmPageName />
            <@stats.gtm />
            <@stats.ga />
        </#if>
        <#if showHeader>
            <@menu.topMenu chapter=chapter />
        </#if>
        <#nested />
        <#if showFooter>
            <footer>
                <div class="container">
                    <div class="co-inline">
                        <div>
                            <strong>Combiq.ru, 2014-2016</strong>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="https://github.com/atott/combiq">https://github.com/atott/combiq</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="/donate">Поддержать проект</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;
                            <a href="http://jira.combiq.ru/">
                                <img style="margin-top: -12px;" src="/static/images/social/jirasoftware_rgb_white_atlassian.png" alt="JIRA">
                            </a>
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
        <script>
            ko.applyBindings({}, document.body);
        </script>
        <@showInstantMessages></@showInstantMessages>
    </body>
</html>
</#macro>

<#macro layoutWithSidebar head='' dsl='' sidebar='' chapter='' subTitle='' pageTitle=''
        mainContainerClass='' sidebarContainerClass=''
        ogDescription='' ogTitle=''>
    <@layoutHtml
                head=head
                dsl=dsl
                chapter=chapter
                subTitle=subTitle
                ogTitle=ogTitle
                ogDescription=ogDescription>
        <#if pageTitle?? && pageTitle != ''>
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
        <div class="container" style="${if(!pageTitle?? || pageTitle == '', 'margin-top: 50px;')}">
            <div class="row">
                <div class="col-md-9 ${mainContainerClass}">
                    <#nested />
                </div>
                <div class="col-md-3 co-sidebar ${sidebarContainerClass}">
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

<#macro layoutBody head='' dsl='' chapter='' showFooter=true subTitle='' gtmPageName='' bodyClass=''>
    <@layoutHtml head=head dsl=dsl chapter=chapter showFooter=showFooter subTitle=subTitle gtmPageName=gtmPageName
            bodyClass=bodyClass>
        <#nested />
    </@layoutHtml>
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
        <co-contenteditor params="
            markdown: '${(content.markdown)!?html?js_string}',
            html: '${(content.html)!?html?js_string}',
            url: '${if(url == '', "/content/" + content.id!, url)}'">
        </co-contenteditor>
    <#else>
        ${(content.html)!''}
    </#if>

    <#global contentEditorIncrementor = contentEditorIncrementor + 1 />
</#macro>

<#macro showInstantMessages>
    <#if instantMessage??>
    <#-- @ftlvariable name="instantMessage" type="ru.atott.combiq.web.view.InstantMessageHolder.Message" -->
    <script>
        $(function() {
            new Dialog('${instantMessage.text?js_string}');
        });
    </script>
    </#if>
</#macro>

<#function explainLevel level>
    <#switch level>
        <#case "D1"><#return "D1 - Junior" />
        <#case "D2"><#return "D2 - Middle" />
        <#case "D3"><#return "D3 - Senior" />
    </#switch>
    <#return level />
</#function>

<#function hasRole roleName>
    <@security.authorize access="hasRole('" + roleName + "')">
        <#return true>
    </@security.authorize>
    <#return false>
</#function>