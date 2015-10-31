<#import "../templates.ftl" as templates />

<#macro sidebar activeMenuItem>
<ul class="co-nav co-nav-right-bordered nav nav-pills nav-stacked">
    <li role="presentation" class="${templates.if(activeMenuItem == 'questionnaires', 'active')}">
        <a href="/questionnaires">
            <span>
                Пройти собеседование
            </span>
            <span class="co-nav-tip">
                Опросники для подготовки к собеседованию на позицию Java разработчик
            </span>
        </a>
    </li>
    <li role="presentation" class="${templates.if(activeMenuItem == 'interview', 'active')}">
        <a href="/questionnaires/interview">
            <span>Провести собеседование</span>
            <span class="co-nav-tip">
                Советы как провести собеседование на позицию Java разработчик
            </span>
        </a>
    </li>
</ul>
</#macro>