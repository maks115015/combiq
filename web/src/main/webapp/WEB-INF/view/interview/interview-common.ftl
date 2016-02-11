<#import "../_layout/templates.ftl" as templates />

<#macro sidebar activeMenuItem>
<ul class="co-nav co-nav-right-bordered nav nav-pills nav-stacked">
    <li role="presentation" class="${templates.if(activeMenuItem == 'interview', 'active')}">
        <a href="/interview">
            <span>
                Пройти собеседование
            </span>
            <span class="co-nav-tip">
                Опросники для подготовки к собеседованию на позицию Java разработчик
            </span>
        </a>
    </li>
    <li role="presentation" class="${templates.if(activeMenuItem == 'arrange', 'active')}">
        <a href="/interview/arrange">
            <span>Провести собеседование</span>
            <span class="co-nav-tip">
                Советы как провести собеседование на позицию Java разработчик
            </span>
        </a>
    </li>
    <li role="presentation" class="${templates.if(activeMenuItem == 'education', 'active')}">
        <a href="/interview/education">
            <span>
                Подготовка Java Dev D2-D3
            </span>
            <span class="co-nav-tip">
                План подготовки к собеседованию на позицию Java разработчик уровня D2-D3
            </span>
        </a>
    </li>
    <li role="presentation" class="${templates.if(activeMenuItem == 'competence', 'active')}">
        <a href="/interview/competence">
            <span>Матрица компетенций</span>
            <span class="co-nav-tip">
                С помощью матрицы вы сможете понять какой уровень профессиональных компентенций вы имеете
            </span>
        </a>
    </li>
</ul>
</#macro>