package ru.atott.combiq.service.question;

import ru.atott.combiq.service.bean.QuestionTag;

import java.util.List;

public interface TagService {
    List<QuestionTag> getTags(List<String> tags);
}
