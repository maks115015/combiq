<#import "../_layout/templates.ftl" as templates />

<#macro sidebar activeMenuItem>
<ul class="co-nav co-nav-right-bordered nav nav-pills nav-stacked">
    <li role="presentation" class="${templates.if(activeMenuItem == 'dashboard', 'active')}">
        <a href="/admin">
            <span>Dashboard</span>
        </a>
    </li>

    <#if templates.hasRole('sa')>
    <li role="presentation" class="${templates.if(activeMenuItem == 'users', 'active')}">
        <a href="/admin/users">
            <span>Пользователи</span>
        </a>
    </li>
    </#if>

    <#if templates.hasRole('sa')>
        <li role="presentation" class="${templates.if(activeMenuItem == 'events', 'active')}">
            <a href="/admin/events">
                <span>События</span>
            </a>
        </li>
    </#if>
</ul>
</#macro>