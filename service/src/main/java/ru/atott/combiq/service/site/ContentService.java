package ru.atott.combiq.service.site;

import ru.atott.combiq.dao.entity.MarkdownContent;

public interface ContentService {

    MarkdownContent getContent(String id);

    void updateContent(String id, String content);
}
