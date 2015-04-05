package ru.atott.combiq.service.question;

import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.ClientHolder;
import ru.atott.combiq.dao.Domains;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.service.bean.QuestionBean;
import ru.atott.combiq.service.mapper.HitToQuestionMapper;
import ru.atott.eshit.client.HitClient;
import ru.atott.eshit.hit.MapHit;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetQuestionServiceImpl implements GetQuestionService {
    @Autowired
    private ClientHolder clientHolder;
    private HitToQuestionMapper hitToQuestionMapper = new HitToQuestionMapper();

    @Override
    public List<QuestionBean> getQuestions(GetQuestionContext context) {
        HitClient client = clientHolder.getClient();
        SearchRequestBuilder requestBuilder = client.getClient()
                .prepareSearch(Domains.question)
                .setTypes(Types.question)
                .setSize((int) context.getSize())
                .setFrom((int) (context.getPage() * context.getSize()));
        SearchResponse searchResponse = requestBuilder.execute().actionGet();
        SearchHit[] hits = searchResponse.getHits().getHits();
        return Lists.newArrayList(hits).stream()
                .map(hit -> new MapHit(field -> hit.getSource().get(field)))
                .map(hitToQuestionMapper::map)
                .collect(Collectors.toList());
    }
}
