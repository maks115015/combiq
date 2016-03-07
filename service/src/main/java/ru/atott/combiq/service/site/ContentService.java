package ru.atott.combiq.service.site;

import ru.atott.combiq.dao.entity.MarkdownContent;

public interface ContentService {

    MarkdownContent getContent(UserContext uc, String id);

    void updateContent(UserContext uc, String id, String content);
}
