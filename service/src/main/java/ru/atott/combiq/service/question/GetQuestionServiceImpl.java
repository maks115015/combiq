package ru.atott.combiq.service.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.entity.QuestionEntity;
import ru.atott.combiq.dao.repository.QuestionRepository;
import ru.atott.combiq.service.bean.Question;

import java.util.stream.Collectors;

@Service
public class GetQuestionServiceImpl implements GetQuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public GetQuestionResponse getQuestions(GetQuestionContext context) {
        Pageable pageable = new PageRequest(context.getPage(), context.getSize());
        Page<QuestionEntity> page = questionRepository.findAll(pageable);

        GetQuestionResponse response = new GetQuestionResponse();
        response.setQuestions(
                page.getContent().stream()
                        .map(e -> {
                            Question question = new Question();
                            question.setTitle(e.getTitle());
                            return question;
                        })
                        .collect(Collectors.toList()));
        return response;
    }
}
