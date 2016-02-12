package ru.atott.combiq.service.site;

import ru.atott.combiq.dao.entity.MarkdownContent;

public interface MarkdownService {

    String toHtml(String markdown);

    MarkdownContent toMarkdownContent(String markdown);

    MarkdownContent toMarkdownContent(String id, String markdown);
}
