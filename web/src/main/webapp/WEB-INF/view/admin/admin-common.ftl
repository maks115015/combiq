<#import "../templates.ftl" as templates />

<#macro sidebar activeMenuItem>
<ul class="co-nav co-nav-right-bordered nav nav-pills nav-stacked">
    <li role="presentation" class="${templates.if(activeMenuItem == 'dashboard', 'active')}">
        <a href="/admin">
            <span>
                Dashboard
            </span>
        </a>
    </li>
</ul>
</#macro>