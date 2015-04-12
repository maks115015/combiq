<#import "templates.ftl" as templates />

<#assign head>
    <script>
        require(['jsx!page/IndexPage'], function(IndexPage) {
            var pageData = {
                questions: ${utils.toJson(questions)}
            };
            window.page = new IndexPage(pageData);
        });
    </script>
</#assign>

<@templates.main head=head>
    <div id="questions"></div>
</@templates.main>