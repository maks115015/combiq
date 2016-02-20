package ru.atott.combiq.service.site;

import org.springframework.data.domain.Page;
import ru.atott.combiq.dao.entity.EventType;
import ru.atott.combiq.dao.entity.Link;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.service.bean.Event;
import ru.atott.combiq.service.bean.UserType;

public interface EventService {

    void createEvent(Context context, EventType type, String text, Link... relevantLinks);

    Page<Event> getEvents(long page, long size);

    default void createPostQuestionCommentEvent(Context context, QuestionEntity questionEntity, String commentId) {
        createEvent(context, EventType.POST_QUESTION_COMMENT,
                "Добавлен новый комментарий к вопросу <b>" + questionEntity.getTitle() + "</b>.",
                new Link("Вопрос", "/questions/" + questionEntity.getId()),
                new Link("Комментарий", "/questions/" + questionEntity.getId() + "#comment-" + commentId));
    }

    default void createEditQuestionCommentEvent(Context context, QuestionEntity questionEntity, String commentId) {
        createEvent(context, EventType.EDIT_QUESTION_COMMENT,
                "Изменён комментарий к вопросу <b>" + questionEntity.getTitle() + "</b>.",
                new Link("Вопрос", "/questions/" + questionEntity.getId()),
                new Link("Комментарий", "/questions/" + questionEntity.getId() + "#comment-" + commentId));
    }

    default void createRegisterUserEvent(UserType userType, String login) {
        createEvent(null, EventType.REGISTER_USER,
                "Зарегистрировался новый пользователь type: " + userType + ", login: " + login);
    }

    default void createQuestion(Context context, QuestionEntity questionEntity){
        String username= context.getUser().getUserName()==null ? "" : " пользователем "+context.getUser().getUserName();
        createEvent(context, EventType.CREATE_QUESTION,
                "Создан вопрос"+username+" <b>" + questionEntity.getTitle() + "</b>.",
                new Link("Вопрос", "/questions/" + questionEntity.getId()));
    }

    default void editQuestion(Context context, QuestionEntity questionEntity){
        String username=" пользователем "+context.getUser().getUserName();
        createEvent(context, EventType.EDIT_QUESTION,
                "Вопрос <b>" + questionEntity.getTitle() + "</b>. Изменен"+username+".",
                new Link("Вопрос", "/questions/" + questionEntity.getId()));
    }
}
