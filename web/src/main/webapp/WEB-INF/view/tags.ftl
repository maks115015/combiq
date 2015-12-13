<#-- @ftlvariable name="tags" type="java.util.List" -->
<#-- @ftlvariable name="tag" type="ru.atott.combiq.service.bean.DetailedQuestionTag" -->
<#import "templates.ftl" as templates />

<@templates.layoutWithSidebar
        pageTitle="Тэги"
        subTitle="Тэги Java вопросов"
        mainContainerClass="page-tags">

    <#list tags?chunk(4) as row>
        <div class="row m-separated-row">
        <#list row as tag>
            <div class="col-md-3">
                <div>
                    <a class="co-tag" href="/questions/tagged/${tag.value?url('utf-8')}">
                        ${tag.value}
                    </a>
                    <span class="co-tag-counter">
                        &nbsp;× ${tag.docCount?c}
                    </span>
                </div>
                <#if tag.details??>
                    <#if tag.details.description??>
                        <span class="co-tag-description">
                            ${tag.details.description}
                        </span>
                    </#if>
                </#if>
            </div>
        </#list>
        </div>
    </#list>
</@templates.layoutWithSidebar>