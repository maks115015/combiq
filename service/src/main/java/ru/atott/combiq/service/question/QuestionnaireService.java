package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Questionnaire;
import ru.atott.combiq.service.bean.QuestionnaireHead;

import java.util.List;

public interface QuestionnaireService {
    List<QuestionnaireHead> getQuestionnaires();
    Questionnaire getQuestionnaire(String id);
}
