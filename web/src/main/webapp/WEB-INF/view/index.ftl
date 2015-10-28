<#import "templates.ftl" as templates />

<#assign head>
    <script type="text/javascript" src="//vk.com/js/api/openapi.js?117"></script>
    <script>
        VK.init({apiId: 5065282, onlyWidgets: true});
    </script>
</#assign>

<@templates.layoutBody head=head subTitle='готовьтесь к Java собеседованию здесь'>
    <div class="co-mainhead">
        <div class="container">
            <div class="row">
                <div class="col-md-5"></div>
                <div class="col-md-5 co-mainhead-content">
                    <p style="margin-bottom: 5px;">
                        <strong>Combiq.ru</strong> - это проект с открытым исходным кодом, цель которого
                        собрать в одном месте всю полезную информацию для Java программистов,
                        которые готовятся к собеседованию на новое место работы.
                    </p>
                    <p>
                        <span id="vk_like" class="co-mainvk-liker"></span>
                    </p>
                    <script type="text/javascript">
                        VK.Widgets.Like("vk_like", {type: "full", height: 24});
                    </script>
                    <p>
                        <img src="/static/images/site/Octocat.png" alt="Octocat">
                        <a href="https://github.com/atott/combiq" class="go-github-btn">
                            Check it out on Github!
                            <span class="arrow">❯</span>
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="co-mainer">
        <div class="container">
            <div>
                <h1>
                    Всё что может вам потребоваться
                    для подготовки к Java собеседованию
                </h1>
                <p>
                    Вопросы для подготовки к собеседованию Java кандидатам <br>
                    и опросники для работадателей
                </p>
            </div>
        </div>
    </div>
    <div class="co-promo-mainer">
        <div class="container text-center">
            <span class="co-promo-mainer-new">NEW <span class="glyphicon glyphicon-ok"></span></span>
            Опросник <a href="/questionnaire/AU7uuDr4o9D60r28WBXV">Java Interview D1-D3</a>
        </div>
    </div>
    <div class="container">
        <div class="row text-center">
            <h2>Что <span class="co-colored">умеет</span> Combiq.ru</h2>
        </div>
        <div class="row text-center">
            <div class="col-md-4">
                <p>
                    <img src="/static/images/search.png" alt="Искать вопросы">
                </p>
                <h3>
                    Вопросы
                </h3>
                <p>
                    У нас большая база вопросов для подготовки к собеседованию.
                    Гибкий поиск, основанный на возможностях <a href="https://www.elastic.co/products/elasticsearch">Elastic Search</a>,
                    поможет вам найти действительно нужные вопросы.
                </p>
                <p>
                    <a href="/questions">
                        <span class="glyphicon glyphicon-circle-arrow-right" aria-hidden="true"></span>
                        Вопросы
                    </a>
                </p>
            </div>
            <div class="col-md-4">
                <p>
                    <img src="/static/images/poll.png" alt="Опросники для собеседования">
                </p>
                <h3>
                    Опросники
                </h3>
                <p>
                    Если вы оказались по другую сторону баррикад и ищете себе сотрудников в
                    компанию, то уже подготовленные списки с вопросами помогут вам найти
                    компетентных специалистов.
                </p>
                <p>
                    <a href="/questionnaires">
                        <span class="glyphicon glyphicon-circle-arrow-right" aria-hidden="true"></span>
                        Опросники
                    </a>
                </p>
            </div>
            <div class="col-md-4">
                <p>
                    <img src="/static/images/plan.png" alt="Планы для подготовки">
                </p>
                <h3>
                    План подготовки
                </h3>
                <p>
                    Наш план подготовки к собеседованию поможет вам охватить
                    все области применения Java. В плане используются только компетентные
                    источники информации.
                </p>
                <p>
                    <a href="/education">
                        <span class="glyphicon glyphicon-circle-arrow-right" aria-hidden="true"></span>
                        План подготовки
                    </a>
                </p>
            </div>
        </div>
    </div>
</@templates.layoutBody>