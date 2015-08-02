package ru.atott.combiq.service.question.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionnaireEntity;
import ru.atott.combiq.dao.repository.QuestionnaireRepository;
import ru.atott.combiq.service.bean.Questionnaire;
import ru.atott.combiq.service.bean.QuestionnaireHead;
import ru.atott.combiq.service.mapper.QuestionnaireHeadMapper;
import ru.atott.combiq.service.question.GetQuestionService;
import ru.atott.combiq.service.question.QuestionnaireService;

import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private GetQuestionService getQuestionService;

    @Override
    public List<QuestionnaireHead> getQuestionnaires() {
        QuestionnaireHeadMapper<QuestionnaireHead> mapper = new QuestionnaireHeadMapper<>(QuestionnaireHead.class);
        return mapper.toList(questionnaireRepository.findAll());
    }

    @Override
    public Questionnaire getQuestionnaire(String id) {
        QuestionnaireEntity entity = questionnaireRepository.findOne(id);

        if (entity == null) {
            return null;
        }

        SearchContext searchContext = new SearchContext();
        searchContext.setQuestionIds(entity.getQuestions());
        searchContext.setSize(400);
        SearchResponse questionsSearchResponse = getQuestionService.getQuestions(searchContext);

        QuestionnaireHeadMapper<Questionnaire> mapper = new QuestionnaireHeadMapper<>(Questionnaire.class);
        Questionnaire questionnaire = mapper.map(entity);
        questionnaire.setQuestions(questionsSearchResponse.getQuestions().getContent());

        return questionnaire;
    }
}
