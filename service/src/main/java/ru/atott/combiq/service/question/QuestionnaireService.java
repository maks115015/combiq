package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Questionnaire;
import ru.atott.combiq.service.bean.QuestionnaireHead;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface QuestionnaireService {
    List<QuestionnaireHead> getQuestionnaires();

    Questionnaire getQuestionnaire(String id);

    Questionnaire exportQuestionnareToPdf(String id, OutputStream outputStream);

    void updateQuestionnaireTitle(String id, String title);

    void exportQuestionnareToPdf(Questionnaire questionnaire, OutputStream outputStream);
}
