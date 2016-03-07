package ru.atott.combiq.service.markdown;

import org.apache.commons.lang3.StringUtils;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.RootNode;
import org.pegdown.plugins.PegDownPlugins;
import org.pegdown.plugins.ToHtmlSerializerPlugin;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.MarkdownContent;
import ru.atott.combiq.service.site.UserContext;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyMap;

@Service
public class MarkdownServiceImpl implements MarkdownService {

    @Override
    public String toHtml(UserContext uc, String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return "";
        }

        PegDownPlugins plugins = new PegDownPlugins.Builder().build();
        PegDownProcessor processor = new PegDownProcessor(0, plugins);
        RootNode rootNode = processor.parseMarkdown(markdown.toCharArray());
        LinkRenderer linkRenderer = new LinkRenderer();
        List<ToHtmlSerializerPlugin> htmlSerializerPlugins = plugins.getHtmlSerializerPlugins();
        ToHtmlSerializer serializer = new CombiqToHtmlSerializer(uc, linkRenderer, emptyMap(), htmlSerializerPlugins);
        return serializer.toHtml(rootNode);
    }

    @Override
    public MarkdownContent toMarkdownContent(UserContext uc, String markdown) {
        return toMarkdownContent(uc, null, markdown);
    }

    @Override
    public MarkdownContent toMarkdownContent(UserContext uc, String id, String markdown) {
        MarkdownContent content = new MarkdownContent();
        content.setId(id);
        content.setHtml(toHtml(uc, markdown));
        content.setMarkdown(markdown);
        return content;
    }
}
