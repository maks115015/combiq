<#import "../templates.ftl" as templates />

<#macro sidebar activeMenuItem>
<ul class="co-nav co-nav-right-bordered nav nav-pills nav-stacked">
    <li role="presentation" class="${templates.if(activeMenuItem == 'job', 'active')}">
        <a href="/job">
            <span>
                Работа
            </span>
            <span class="co-nav-tip">
                Предложения о работе на позицию Java разработчик
            </span>
        </a>
    </li>
    <li role="presentation" class="${templates.if(activeMenuItem == 'opinions', 'active')}">
        <a href="/job/opinions">
            <span>Мнения</span>
            <span class="co-nav-tip">
                Обсуждение компаний, вакансий, условий труда
            </span>
        </a>
    </li>
</ul>
</#macro>