package ru.atott.combiq.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import ru.atott.combiq.dao.Types;

import java.util.List;

@Document(indexName = "#{domainResolver.resolveQuestionIndex()}", type = Types.jdk8classes)
public class Jdk8ClassEntity {
    @Id
    private String id;
    private List<String> classNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }
}
