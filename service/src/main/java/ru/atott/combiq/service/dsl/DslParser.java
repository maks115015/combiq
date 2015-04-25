package ru.atott.combiq.service.dsl;

import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;

import java.util.List;
import java.util.stream.Collectors;

public class DslParser {
    private static Parser<DslQuery> parser;

    public static DslQuery parse(String dsl) {
        if (StringUtils.isBlank(dsl)) {
            return new DslQuery();
        }
        if (parser == null) {
            parser = getDslQueryParser();
        }
        return parser.parse(dsl);
    }

    private static Parser<DslQuery> getDslQueryParser() {
        Parser<Void> ignored = Parsers.never();

        Terminals operators = Terminals.operators("[", "]", " ", ":");
        Parser<String> singleQuoteTokenizer = Terminals.StringLiteral.SINGLE_QUOTE_TOKENIZER;
        Parser<?> wordTokenizer = Scanners.notAmong("[]:").many().source();
        Parser<?> tokenizer = Parsers.or(operators.tokenizer(), singleQuoteTokenizer, wordTokenizer);

        // ---

        Parser<String> wordParser = Parsers.tokenType(String.class, "string");

        Parser<DslTag> tagParser = wordParser
                .between(operators.token("["), operators.token("]"))
                .map(DslTag::new);

        Parser<DslTerm> termParser = wordParser
                .map(DslTerm::new);

        Parser<DefaultKeyValue> pairParser = wordParser
                .followedBy(operators.token(":"))
                .next(key -> wordParser.map(value -> new DefaultKeyValue(key, value)));

        Parser<Object> conditionsParser = Parsers.or(tagParser, pairParser, termParser);

        Parser<DslQuery> queryParser = conditionsParser
                .sepBy(operators.token(" ").many())
                .map(conditions -> {
                    List<DslTag> tags = conditions.stream()
                            .filter(condition -> condition instanceof DslTag)
                            .map(DslTag.class::cast)
                            .collect(Collectors.toList());

                    List<DslTerm> terms = conditions.stream()
                            .filter(condition -> condition instanceof DslTerm)
                            .map(DslTerm.class::cast)
                            .collect(Collectors.toList());

                    DslQuery query = new DslQuery();
                    query.setTags(tags);
                    query.setTerms(terms);

                    List<DefaultKeyValue> pairs = conditions.stream()
                            .filter(condition -> condition instanceof DefaultKeyValue)
                            .map(DefaultKeyValue.class::cast)
                            .collect(Collectors.toList());

                    pairs.forEach(pair -> {
                        switch ((String) pair.getKey()) {
                            case "level":
                                query.setLevel((String) pair.getValue());
                                break;
                        }
                    });

                    return query;
                });

        return queryParser.from(tokenizer, ignored.skipMany());
    }
}
