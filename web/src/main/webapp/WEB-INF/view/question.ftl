<#import "templates.ftl" as templates />

<#assign head>

</#assign>

<@templates.layoutWithoutSidebar head=head dsl=dsl>
    <div class="co-question">
        <div class="co-flex">
            <div class="co-question-reputation">
                <div class="co-question-reputation-value">
                    <div>
                        <a class="js-questionReputationUp-${question.id} ${templates.if(questionReputationVotedUp(), 'co-voted')} co-reputation-up" onclick="questionReputationVote(true, '${question.id?js_string}'); return false;" href="#"></a>
                    </div>
                    <span class="js-questionReputationLabel-${question.id}">${question.reputation}</span>
                    <div>
                        <a class="js-questionReputationDown-${question.id} ${templates.if(questionReputationVotedDown(), 'co-voted')} co-reputation-down" onclick="questionReputationVote(false, '${question.id?js_string}'); return false;" href="#"></a>
                    </div>
                </div>
                <div class="co-question-reputation-label">
                    Репутация
                </div>
            </div>
            <div class="co-question-content">
                <div class="co-question-title">
                    ${question.title}
                </div>
                <@questionStaff />
            </div>
        </div>
    </div>
    <@questionPosition />
</@templates.layoutWithoutSidebar>

<#macro questionStaff>
    <ul class="co-question-staff">
        <#if question.level??>
            <li class="co-question-staff-level">
                Уровень <strong><a href="/questions/level/${question.level}">${question.level}</a></strong>
            </li>
        </#if>
        <#list question.tags as tag>
            <li>
                <co-tag tag="${tag}">${tag}</co-tag>
            </li>
        </#list>
    </ul>
</#macro>

<#macro questionPosition>
    <#if position??>
        <div>
            <ul class="co-question-position">
                <#if position.previosQuestionId??>
                    <li>
                        <a href="/questions/${position.previosQuestionId}?index=${position.index - 1}&dsl=${dsl?url}">
                            <span class="co-arrow">←</span> предыдущий
                        </a>
                    </li>
                </#if>
                <li>
                    <strong>${position.index + 1}</strong> из <a href="/questions/search?q=${dsl?url}">${position.total}</a>
                </li>
                <#if position.nextQuestionId??>
                    <li>
                        <a href="/questions/${position.nextQuestionId}?index=${position.index + 1}&dsl=${dsl?url}">
                            следующий <span class="co-arrow">→</span>
                        </a>
                    </li>
                </#if>
            </ul>
        </div>
        <div class="clearfix"></div>
    </#if>
</#macro>

<#function questionReputationVotedUp>
    <#if user?? && question.attrs?? && question.attrs.reputation?? && question.attrs.reputation &gt; 0>
        <#return true />
    </#if>
    <#return false />
</#function>

<#function questionReputationVotedDown>
    <#if user?? && question.attrs?? && question.attrs.reputation?? && question.attrs.reputation &lt; 0>
        <#return true />
    </#if>
    <#return false />
</#function>