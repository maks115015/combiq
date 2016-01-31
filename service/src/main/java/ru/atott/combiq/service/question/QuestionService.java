package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.Question;
import ru.atott.combiq.service.site.Context;

import java.util.List;

public interface QuestionService {

    void saveUserComment(String userId, String questionId, String comment);

    void saveComment(Context context, String questionId, String comment);

    void updateComment(Context context, String questionId, String commentId, String comment);

    void saveQuestionBody(String questionId, String body);

    List<String> refreshMentionedClassNames(Question question);
}
