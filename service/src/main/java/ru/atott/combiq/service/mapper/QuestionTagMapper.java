package ru.atott.combiq.service.mapper;

import ru.atott.combiq.dao.entity.TagEntity;
import ru.atott.combiq.service.bean.QuestionTag;

public class QuestionTagMapper implements Mapper<TagEntity, QuestionTag> {
    @Override
    public QuestionTag map(TagEntity source) {
        QuestionTag tag = new QuestionTag();
        tag.setTag(source.getName());
        tag.setSuggestViewOthersQuestionsLabel(source.getSuggestViewOthersQuestionsLabel());
        tag.setDescription(source.getDescription());
        return tag;
    }
}
