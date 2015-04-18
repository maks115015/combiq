<#macro layoutHtml head=''>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Combiq - вопросы для собеседования Java</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="/static/css/styles.css">
        <link rel="stylesheet" href="/static/font/roboto/roboto.css">
        <link rel="stylesheet" href="/static/font/comfortaa/comfortaa.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="/static/bower_components/webcomponentsjs/webcomponents.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.17/require.min.js"></script>

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
        </script>


        <link rel="import" href="/static/bower_components/core-toolbar/core-toolbar.html">
        <link rel="import" href="/static/bower_components/paper-icon-button/paper-icon-button.html">
        ${head}
    </head>
    <body>
        <nav class="navbar navbar-default navbar-static-top co-header">
            <ul class="co-topmenu">
                <li>
                    <a class="co-topmenu-mainer" href="/">Combiq.ru</a>
                </li>
                <li>
                    <a class="co-out" href="https://github.com/atott/combiq">на Github.com</a>
                </li>
                <li>
                    <a class="co-out" href="https://github.com/atott/combiq/wiki">о проекте</a>
                </li>
                <li class="co-getstarted">
                    <a href="/questions">Вопросы</a>
                </li>
            </ul>
        </nav>
        <#nested />
    </body>
</html>
</#macro>

<#macro layoutWithSidebar head=''>
    <@layoutHtml head=head>
        <div class="container">
            <div class="col-md-9">
                <#nested />
            </div>
            <div class="col-md-3">
                <nav class="bs-docs-sidebar">
                    <ul id="sidebar" class="nav nav-stacked fixed">
                        <li>
                            <a href="https://github.com/atott/combiq/wiki/%D0%9E-%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B5">О проекте</a>
                            <a href="https://github.com/atott/combiq">Присоединяйся к Combiq.ru на Github.com</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </@layoutHtml>
</#macro>

<#macro layoutBody head=''>
    <@layoutHtml head=head>
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