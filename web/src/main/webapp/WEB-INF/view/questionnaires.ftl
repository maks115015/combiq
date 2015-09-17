<#import "templates.ftl" as templates />

<@templates.layoutWithSidebar head=head chapter='questionnaires' subTitle='Опросники' sidebar='&nbsp;'>
    <h1>Опросники для собеседований</h1>
    <p>
        Для того чтобы провести собеседование эффективно порой нужно готовиться тщательнее чем в случае когда бы
        вы сами были кандидатом. Обязательно прочитайте статьи из списка ниже, они помогут вам избежать типичных
        ошибок.
    </p>
    <ul>
        <li>
            <a href="http://articles.javatalks.ru/articles/11">Как проводить собеседования?</a>,
            <a href="http://articles.javatalks.ru/users/%D0%A1%D1%82%D0%B0%D1%80%D0%BE%D0%B2%D0%B5%D1%80%D1%8A">Староверъ</a>,
            <a href="http://articles.javatalks.ru/">javatalks.ru</a>,
            2013.
        </li>
    </ul>
    <p>
        Ниже представлены несколько опросников, которые, возможно, помогут вам провести техническое собеседование
        кандидатов на вакансию Java разработчик разных уровней (D1-D3).
    </p>
    <ul>
        <#list questionnaires as questionnaire>
            <li>
                <a href="/questionnaire/${questionnaire.id}">
                    ${questionnaire.name}
                </a>
                <#if questionnaire.title??>
                    <p>
                        ${questionnaire.title.html?html}
                    </p>
                </#if>
            </li>
        </#list>
    </ul>
</@templates.layoutWithSidebar>