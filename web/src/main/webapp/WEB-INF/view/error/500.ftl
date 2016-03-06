<#import '../_layout/templates.ftl' as templates>

<@templates.layoutBody showFooter=false>

    <div class="container">
        <div>
            <h1>500 - Произошла ошибка</h1>
        </div>

        <div class="co-large" style="margin-bottom: 40px;">
            По адресу <strong>${requestUrl!}</strong> произошла ошибка.
        </div>
        <div class="row" style="margin-bottom: 40px;">
            <div class="col-md-8">
                <p class="">
                    Мы исправим ошибку быстрее если вы сообщите о ней в нашем багтрекере
                    <a href="http://jira.combiq.ru">http://jira.combiq.ru</a>.
                    <br>
                    Возможно, вы сами захотите исправить эту ошибку? :) Combiq.ru -
                    <a href="/project">это проект с открытым исходным кодом</a>.
                </p>
            </div>
        </div>
        <pre class="co-small">${stacktrace!}</pre>
    </div>

</@templates.layoutBody>