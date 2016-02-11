<#import 'functions.ftl' as f>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro topMenu chapter=''>
    <header>
        <div class="co-menu-top">
            <div class="container">
                <div class="row">
                    <div class="col-md-2">
                        <a class="co-topmenu-mainer" href="/" title="Combiq.ru - Всё, что может потребоваться для подготовки к Java собеседованию" style="line-height: 48px;">
                            <img style="margin-bottom: -8px;" src="/static/images/site/flat-logo-64.png?v=1" />
                        </a>
                    </div>
                    <div class="col-md-7">
                        <ul class="co-menu-top-items">
                            <li class="${f.if(chapter == 'questions', 'active')}">
                                <a href="/questions">Вопросы</a>
                            </li>
                            <li class="${f.if(chapter == 'interview', 'active')}">
                                <a href="/interview">Собеседование</a>
                            </li>
                            <li class="${f.if(chapter == 'job', 'active')}">
                                <a href="/job">Работа</a>
                            </li>
                            <@security.authorize access="hasRole('sa')">
                            <li class="${f.if(chapter == 'admin', 'active')}">
                                <a href="/admin">Админка</a>
                            </li>
                            </@security.authorize>
                        </ul>
                    </div>
                    <div class="col-md-3">
                        <ul class="co-menu-top-items pull-right">
                            <#if user??>
                                <li class="co-auth">
                                    <a class="co-inline" href="/logout.do" ">
                                        <#if user.headAvatarUrl??>
                                            <img style="position: absolute; margin-top: -12px; margin-left: -10px;" width="46" height="46" src="${user.headAvatarUrl!}">
                                        </#if>
                                        <span class="inline" style="${f.if(user.headAvatarUrl??, 'margin-left: 50px;')}">Выйти</span>
                                    </a>
                                </li>
                            <#else>
                                <li class="co-auth">
                                    <a href="/login.do">Войти</a>
                                </li>
                            </#if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </header>
</#macro>