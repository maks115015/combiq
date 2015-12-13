package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.DetailedQuestionTag;
import ru.atott.combiq.service.bean.QuestionTag;

import java.util.List;
import java.util.Map;

public interface TagService {

    List<QuestionTag> getTags(List<String> tags);

    Map<String, QuestionTag> getTagsMap(List<String> tags);

    List<QuestionTag> getTags();

    List<DetailedQuestionTag> getAllQuestionTags();
}
