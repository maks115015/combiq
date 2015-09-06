package ru.atott.combiq.service.question.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.TagEntity;
import ru.atott.combiq.dao.repository.TagRepository;
import ru.atott.combiq.service.bean.QuestionTag;
import ru.atott.combiq.service.mapper.QuestionTagMapper;
import ru.atott.combiq.service.question.TagService;

import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<QuestionTag> getTags(List<String> tags) {
        if (CollectionUtils.isEmpty(tags)) {
            return Collections.emptyList();
        }

        Iterable<TagEntity> tagEntities = tagRepository.findAll(tags);
        QuestionTagMapper mapper = new QuestionTagMapper();
        return mapper.toList(tagEntities);
    }
}
