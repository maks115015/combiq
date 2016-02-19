package ru.atott.combiq.web.request;
import ru.atott.combiq.dao.entity.MarkdownContent;
import java.util.List;

/**
 * Created by Леонид on 19.02.2016.
 */
public class QuestionRequest extends ContentRequest{
    private String id;
    private String title;
    private List<String> tags;
    private String level;
    private MarkdownContent body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public MarkdownContent getBody() {
        return body;
    }

    public void setBody(MarkdownContent body) {
        this.body = body;
    }
}
