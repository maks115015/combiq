<#import "../templates.ftl" as templates />

<#macro sidebar activeMenuItem>
<ul class="co-nav co-nav-right-bordered nav nav-pills nav-stacked">
    <li role="presentation" class="${templates.if(activeMenuItem == 'education', 'active')}">
        <a href="/education">
            <span>
                Подготовка Java Dev D2-D3
            </span>
            <span class="co-nav-tip">
                План подготовки к собеседованию на позицию Java разработчик уровня D2-D3
            </span>
        </a>
    </li>
    <li role="presentation" class="${templates.if(activeMenuItem == 'competence', 'active')}">
        <a href="/education/competence">
            <span>Матрица компетенций</span>
            <span class="co-nav-tip">
                С помощью матрицы вы сможете понять какой уровень профессиональных компентенций вы имеете
            </span>
        </a>
    </li>
</ul>
</#macro>