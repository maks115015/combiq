package ru.atott.combiq.data.service.impl;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atott.combiq.dao.Types;
import ru.atott.combiq.dao.es.NameVersionDomainResolver;
import ru.atott.combiq.data.service.ImportService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Service
public class ImportServiceImpl implements ImportService {
    @Autowired(required = false)
    private Client client;
    @Autowired
    private NameVersionDomainResolver domainResolver;

    @Override
    public String importQuestionnareOds(String filename, String questionnaireName) throws Exception {
        SpreadsheetDocument document = SpreadsheetDocument.loadDocument(new File(filename));
        Table sheet = document.getSheetByIndex(0);

        List<String> questions = new ArrayList<>();

        for (int i = 1; i < sheet.getRowList().size(); i++) {
            Row row = sheet.getRowList().get(i);

            String question = getCellStringValue(row, 0);

            if (StringUtils.isBlank(question)) {
                break;
            }

            String levelString = getCellStringValue(row, 1);
            long level = Long.valueOf(StringUtils.replace(levelString, "D", ""));
            String tagsString = getCellStringValue(row, 2);
            List<String> tags = Collections.emptyList();
            if (StringUtils.isNotBlank(tagsString)) {
                tags = Lists.newArrayList(StringUtils.split(tagsString, ", "));
            }
            String tip = StringUtils.defaultIfBlank(getCellStringValue(row, 3), null);


            XContentBuilder builder = jsonBuilder()
                    .startObject()
                        .field("title", question)
                        .field("level", level)
                        .field("tags", tags)
                        .field("tip", tip)
                    .endObject();

            IndexResponse indexResponse = client
                    .prepareIndex(domainResolver.resolveQuestionIndex(), Types.question)
                    .setSource(builder)
                    .execute()
                    .actionGet();

            questions.add(indexResponse.getId());
        }

        XContentBuilder questionnaireBuilder = jsonBuilder()
                .startObject()
                    .field("name", questionnaireName)
                    .field("questions", questions)
                .endObject();

        IndexResponse indexResponse = client
                .prepareIndex(domainResolver.resolveQuestionIndex(), Types.questionnaire)
                .setSource(questionnaireBuilder)
                .execute()
                .actionGet();

        return indexResponse.getId();
    }

    private String getCellStringValue(Row row, int index) {
        if (row.getCellByIndex(index) == null) {
            return null;
        }
        return row.getCellByIndex(index).getStringValue();
    }
}
