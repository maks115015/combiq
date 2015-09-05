<#import "templates.ftl" as templates />

<@templates.layoutBody head=head showFooter=false>
    <div class="container">
        <h1>Войти на combiq.ru</h1>
        <div class="text-center" style="padding: 100px 0">
            <p>
                <a class="go-github-btn" href="https://github.com/login/oauth/authorize?client_id=${githubClientId}&scope=user">
                    Войти через Github.com
                    <span class="arrow">❯</span>
                </a>
            </p>
        </div>
    </div>
</@templates.layoutBody>