<#-- @ftlvariable name="tags" type="java.util.List" -->
<#-- @ftlvariable name="tag" type="ru.atott.combiq.service.bean.DetailedQuestionTag" -->
<#import "_layout/templates.ftl" as templates />
<#import "_layout/functions.ftl" as functions />

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
                <#if functions.hasRole('sa') || functions.hasRole('contenter')>
                    <div class="co-small">
                        <a href="javascript:void(0)" onclick="
                                ko.openDialog('co-edittag', {
                                    tag: '${tag.value?html?js_string}',
                                    description: '${(tag.details.description)!?html?js_string}',
                                    suggestViewOthersQuestionsLabel: '${(tag.details.suggestViewOthersQuestionsLabel)!?html?js_string}'
                                })">
                            Изменить
                        </a>
                    </div>
                </#if>
            </div>
        </#list>
        </div>
    </#list>
</@templates.layoutWithSidebar>