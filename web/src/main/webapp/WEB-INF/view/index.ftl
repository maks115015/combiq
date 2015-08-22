<#import "templates.ftl" as templates />

<#assign head>

</#assign>

<@templates.layoutBody head=head>
    <div class="co-mainhead">
        <div class="container">
            <div class="row">
                <div class="col-md-5"></div>
                <div class="col-md-5 co-mainhead-content">
                    <p>
                        <strong>Combiq.ru</strong> - это проект с открытым исходным кодом, цель которого
                        собрать в одном месте всю полезную информацию для программистов,
                        которые готовятся к собеседованию на новое место работы.
                    </p>
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
                <h3>
                    Combiq.ru
                </h3>
                <h1>
                    Всё что может вам потребоваться
                    для подготовки к Java собеседованию
                </h1>
                <p>
                    Вопросы для подготовки к собеседованию Java кандидатам <br>
                    и опросники для работадателей.
                </p>
            </div>
        </div>
    </div>
</@templates.layoutBody>