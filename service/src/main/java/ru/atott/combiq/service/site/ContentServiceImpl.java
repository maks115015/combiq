package ru.atott.combiq.service.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.ContentEntity;
import ru.atott.combiq.dao.entity.MarkdownContent;
import ru.atott.combiq.dao.repository.ContentRepository;
import ru.atott.combiq.service.markdown.MarkdownService;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private MarkdownService markdownService;

    @Override
    public MarkdownContent getContent(UserContext uc, String id) {
        ContentEntity contentEntity = contentRepository.findOne(id);

        if (contentEntity == null) {
            return markdownService.toMarkdownContent(uc, id, null);
        }

        MarkdownContent content = contentEntity.getContent();

        if (content.getId() == null) {
            content.setId(id);
            contentRepository.save(contentEntity);
        }

        return content;
    }

    @Override
    public void updateContent(UserContext uc, String id, String content) {
        ContentEntity contentEntity = contentRepository.findOne(id);

        if (contentEntity == null) {
            contentEntity = new ContentEntity();
            contentEntity.setId(id);
        }

        contentEntity.setContent(markdownService.toMarkdownContent(uc, id, content));
        contentRepository.save(contentEntity);
    }
}
