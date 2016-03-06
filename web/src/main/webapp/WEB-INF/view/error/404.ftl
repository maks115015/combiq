<#import '../_layout/templates.ftl' as templates>

<@templates.layoutBody showFooter=false>

    <div class="container">
        <div>
            <h1>404 - Не нашли страницу</h1>
        </div>

        <div class="co-large">
            По адресу <strong>${requestUrl!}</strong> ничего нет. Возможно:
        </div>
        <ul style="margin-top: 10px;">
            <li class="co-large">
                неправильно набран адрес;
            </li>
            <li class="co-large">
                такой страницы никогда не было на этом сайте;
            </li>
            <li class="co-large">
                такая страница была, но по этому адресу ее больше нет.
            </li>
        </ul>

        <div style="margin-top: 45px;">
            Возможно, вам будет интересно что-то сюда добавить? :) Combiq.ru -
            <a href="/project">это проект с открытым исходным кодом</a>, присоединяйтесь!
        </div>
    </div>

</@templates.layoutBody>