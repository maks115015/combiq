<#import "templates.ftl" as templates />

<@templates.layoutBody head=head showFooter=true gtmPageName='login'>
    <div class="container" style="min-height: 400px;">
        <h1>Войти на combiq.ru</h1>
        <div class="subtitle">
            Пользуйтесь всеми возможностями сайта без ограничений
        </div>

        <div class="row">
            <div class="col-md-6 co-login-left">
                <div class="row">
                    <div class="col-md-4">
                        <img src="/static/images/login/co-login-search.png" alt="Поиск">
                    </div>
                    <div class="col-md-8">
                        <a href="/questions">Ищите вопросы</a> для подготовки к собеседованию используя богатые возможности нашего поиска.
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <img src="/static/images/login/co-login-memory.png" alt="Комментарии к вопросам">
                    </div>
                    <div class="col-md-8">
                        Оставляйте комментарии к вопросам, чтобы быстро вспомнить на них ответы.
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <img src="/static/images/login/co-login-list.png" alt="Опросники">
                    </div>
                    <div class="col-md-8">
                        Скачивайте и распечатывайте <a href="/questionnaires">подобранные опросники</a> для подготовки к собеседованию offline.
                    </div>
                </div>
            </div>
            <div class="col-md-6 co-login-right">
                <div class="text-center" style="padding: 100px 0">
                    <p>
                        <a id="loginButton" class="go-github-btn" href="https://github.com/login/oauth/authorize?client_id=${githubClientId}&scope=user">
                            Войти через Github.com
                            <span class="arrow">❯</span>
                        </a>
                    </p>
                </div>
            </div>
        </div>
    </div>
</@templates.layoutBody>