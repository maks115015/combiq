package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Questionnaire;
import ru.atott.combiq.service.bean.QuestionnaireHead;
import ru.atott.combiq.service.site.UserContext;

import java.io.OutputStream;
import java.util.List;

public interface QuestionnaireService {

    List<QuestionnaireHead> getQuestionnaires();

    QuestionnaireHead getQuestionnaireByLegacyId(String id);

    Questionnaire getQuestionnaire(UserContext uc, String id);

    Questionnaire exportQuestionnareToPdf(UserContext uc, String id, OutputStream outputStream);

    void updateQuestionnaireTitle(String id, String title);

    void exportQuestionnareToPdf(Questionnaire questionnaire, OutputStream outputStream);
}
