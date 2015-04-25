package ru.atott.combiq.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.service.dsl.DslTag;
import ru.atott.combiq.service.mapper.QuestionEntityToQuestionMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetQuestionServiceImpl implements GetQuestionService {
    private QuestionEntityToQuestionMapper questionMapper = new QuestionEntityToQuestionMapper();

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public GetQuestionResponse getQuestions(GetQuestionContext context) {
        Pageable pageable = new PageRequest(context.getPage(), context.getSize());

        Page<QuestionEntity> page = null;

        if (context.getDslQuery().getTags().isEmpty()) {
            page = questionRepository.findAll(pageable);
        } else {
            List<String> tagValues = context.getDslQuery().getTags().stream()
                    .map(DslTag::getValue)
                    .collect(Collectors.toList());
            page = questionRepository.findByTagsIn(tagValues, pageable);
        }

        GetQuestionResponse response = new GetQuestionResponse();
        response.setQuestions(page.map(questionMapper::map));
        return response;
    }
}
