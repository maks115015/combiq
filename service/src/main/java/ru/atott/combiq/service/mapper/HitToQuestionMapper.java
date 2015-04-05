package ru.atott.combiq.service.mapper;

import ru.atott.combiq.service.bean.QuestionBean;
import ru.atott.eshit.hit.Hit;

public class HitToQuestionMapper implements Mapper<Hit, QuestionBean> {
    @Override
    public QuestionBean map(Hit hit) {
        QuestionBean bean = new QuestionBean();
        bean.setTitle(hit.getString("title"));
        return bean;
    }
}
