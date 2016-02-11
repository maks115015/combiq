<#import "_layout/templates.ftl" as templates />



<#assign sidebar>
    <div>
        <h4>Интересное</h4>
        <ol class="list-unstyled co-question-aside-tips">
            <li>
                <div class="row">
                    <div class="col-md-1">
                        <span class="glyphicon glyphicon-link" aria-hidden="true"></span>
                    </div>
                    <div class="col-md-10">
                        Freak вопросы, которые вам могут задать на собеседовании.
                        <a href="/questions/tagged/freak">Будьте осторожны →</a>
                    </div>
                </div>
            </li>
        </ol>
    </div>

    <div>
        <h4>Популярное</h4>
        <ul class="co-popularTags">
           <#list popularTags as tag>
            <li>
                <a  class="co-tag" href="/questions/tagged/${tag.value}">
                   ${tag.value}
                </a>
                   <#if tag.docCount??>
                       <span class="co-tag-counter">
                           &nbsp;× ${tag.docCount}
                       </span>
                   </#if>
            </li>
           </#list>
        </ul>
        <p>
            <a href="/tags">Смотреть все тэги →</a>
        </p>
    </div>
</#assign>

<#assign pageTitle>
    <#if questionsCatalog>
        Вопросы
    </#if>
</#assign>

<@templates.layoutWithSidebar head=head dsl=dsl sidebar=sidebar chapter='questions' subTitle=subTitle!'' pageTitle=pageTitle>
    <form action="/questions/search" method="get" id="searchForm">
        <div class="co-search">
            <h4>Поисковый запрос</h4>
                <div class="co-flex">
                    <input autocomplete="off" type="text" name="q" value="${dsl!}"/>
                    <button onclick="document.getElementById('searchForm').submit();" style="margin-left: 10px" >
                        Искать
                    </button>
                </div>
        </div>
        <div class="co-search-help-tip">
            <a href="https://github.com/atott/combiq/wiki/%D0%9F%D0%BE%D0%B8%D1%81%D0%BA">
                Вы можете задавать гибкие условия поиска, например, по тэгам или уровню <span class="co-arrow">→</span>
            </a>
        </div>
    </form>

    <#-- Advices -->
    <#if showMatrixCompetenceAdvice?? && showMatrixCompetenceAdvice!false>
        <div class="co-search-advice">
            <span class="glyphicon glyphicon-link" aria-hidden="true"></span>
            Ознакомьтесь с <a href="/education/competence">матрицей компетенций Java разработчиков</a>, чтобы
            определить уровень знаний.
        </div>
    </#if>
    <ul class="co-questions">
    <#if questions?size==0>К сожалению по такому запросу ничего не найдено</#if>
        <#list questions as question>
            <li>
              <div class="co-component-question">
                    <div class="co-component-question-level-bound">
                        <div class="co-component-question-level">
                            <div class="co-component-question-level-title">
                                <a href="/questions/level/${question.level}">${question.level}</a>
                            </div>
                            <div class="co-component-question-level-desc">уровень</div>
                        </div>
                    </div>
                    <div class="co-component-question-content">
                        <div class="co-component-question-text">
                            <a href="/questions/${question.id}?index=${paging.from + question_index}&dsl=${dsl?url}">${question.title}</a>
                        </div>
                        <div>
                            <ul class="co-component-question-tags">
                                <#list question.tags as tag>
                                  <li>
                                     <a class="co-tag" href="/questions/tagged/${tag}">
                                        ${tag}
                                     </a>
                                   </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
              </div>
            </li>
        </#list>
    </ul>
    <@templates.paging paging=paging />
</@templates.layoutWithSidebar>