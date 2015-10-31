package ru.atott.combiq.service.mapper;

import org.springframework.beans.BeanUtils;
import ru.atott.combiq.dao.entity.MarkdownContent;
import ru.atott.combiq.dao.entity.QuestionnaireEntity;
import ru.atott.combiq.service.bean.QuestionnaireHead;

public class QuestionnaireHeadMapper<T extends QuestionnaireHead> implements Mapper<QuestionnaireEntity, T> {
    private Class<? extends T> clazz;

    public QuestionnaireHeadMapper(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T map(QuestionnaireEntity source) {
        T head = BeanUtils.instantiate(clazz);
        head.setName(source.getName());
        head.setId(source.getId());
        head.setQuestionsCount(source.getQuestions() == null ? 0 : source.getQuestions().size());
        head.setTitle(source.getTitle() != null ? source.getTitle() : new MarkdownContent(null, null));
        return head;
    }
}
