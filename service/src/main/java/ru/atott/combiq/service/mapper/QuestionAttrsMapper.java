package ru.atott.combiq.service.mapper;

import ru.atott.combiq.dao.entity.QuestionAttrsEntity;
import ru.atott.combiq.service.bean.QuestionAttrs;

public class QuestionAttrsMapper implements Mapper<QuestionAttrsEntity, QuestionAttrs> {
    @Override
    public QuestionAttrs map(QuestionAttrsEntity source) {
        QuestionAttrs attrs = new QuestionAttrs();
        attrs.setUserId(source.getUserId());
        attrs.setQuestionId(source.getQuestionId());
        attrs.setReputation(source.getReputation());
        attrs.setComment(source.getComment());
        return attrs;
    }
}
