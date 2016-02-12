package ru.atott.combiq.service.site;

import org.apache.commons.lang3.StringUtils;
import org.pegdown.PegDownProcessor;
import org.pegdown.plugins.PegDownPlugins;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.MarkdownContent;

@Service
public class MarkdownServiceImpl implements MarkdownService {

    @Override
    public String toHtml(String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return "";
        }

        PegDownPlugins plugins = new PegDownPlugins.Builder().build();
        PegDownProcessor processor = new PegDownProcessor(0, plugins);
        return processor.markdownToHtml(markdown);
    }

    @Override
    public MarkdownContent toMarkdownContent(String markdown) {
        return toMarkdownContent(null, markdown);
    }

    @Override
    public MarkdownContent toMarkdownContent(String id, String markdown) {
        MarkdownContent content = new MarkdownContent();
        content.setId(id);
        content.setHtml(toHtml(markdown));
        content.setMarkdown(markdown);
        return content;
    }
}
