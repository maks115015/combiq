<#import "../_layout/templates.ftl" as templates />
<#import "admin-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='posts' />
</#assign>

<@templates.layoutWithSidebar
        chapter='admin'
        subTitle='Статьи из раздела Блог'
        pageTitle='Статьи'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <p>
        <a href="/admin/posts/edit" class="btn">Создать новую статью</a>
    </p>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Заголовок</th>
                <th>Дата создания</th>
                <th>-</th>
            </tr>
        </thead>
        <tbody>
            <#list posts as post>
                <#-- @ftlvariable name="post" type="ru.atott.combiq.service.bean.Post" -->

                <tr>
                    <td>
                        <strong>${post.title}</strong>
                    </td>
                    <td>
                        ${post.createDate?string["dd.MM.yyyy"]}
                    </td>
                    <td>
                        <button>Изменить</button>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>

    <@templates.paging paging=paging />
</@templates.layoutWithSidebar>