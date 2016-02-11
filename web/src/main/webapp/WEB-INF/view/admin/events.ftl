<#import "../_layout/templates.ftl" as templates />
<#import "admin-common.ftl" as common />

<#assign sidebar>
    <@common.sidebar activeMenuItem='events' />
</#assign>

<@templates.layoutWithSidebar
        chapter='admin'
        subTitle='События системы'
        pageTitle='События'
        sidebar=sidebar
        mainContainerClass='co-rightbordered'>

    <table class="table table-bordered co-small">
        <thead>
        <tr>
            <th>Дата</th>
            <th>Источник</th>
            <th>Событие</th>
        </tr>
        </thead>
        <tbody>
            <#list events as event>
            <#-- @ftlvariable name="event" type="ru.atott.combiq.service.bean.Event" -->

            <tr>
                <td>
                    ${event.createDate?string["dd.MM.yyyy hh:mm"]}
                </td>
                <td>
                    <#if event.creatorUserName??>
                        <span>${event.creatorUserName}</span>
                    </#if>
                </td>
                <td>
                    <div>
                        ${event.message}
                    </div>
                    <#if event.relevantLinks??>
                        <div>
                            <#list event.relevantLinks as link>
                                <a href="${link.url}">${link.text}</a><#if link_has_next>, </#if>
                            </#list>
                        </div>
                    </#if>
                </td>
            </tr>
            </#list>
        </tbody>
    </table>

    <@templates.paging paging=paging />
</@templates.layoutWithSidebar>