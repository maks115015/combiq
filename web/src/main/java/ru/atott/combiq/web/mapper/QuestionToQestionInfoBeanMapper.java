package ru.atott.combiq.web.mapper;

import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.mapper.Mapper;
import ru.atott.combiq.web.bean.QuestionInfoBean;

public class QuestionToQestionInfoBeanMapper implements Mapper<Question, QuestionInfoBean> {
    @Override
    public QuestionInfoBean map(Question source) {
        QuestionInfoBean bean = new QuestionInfoBean();
        bean.setId(source.getId());
        bean.setTitle(source.getTitle());
        return bean;
    }
}
