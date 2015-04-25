package ru.atott.combiq.service.dsl;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;

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

        Terminals operators = Terminals.operators("[", "]", " ");
        Parser<String> singleQuoteTokenizer = Terminals.StringLiteral.SINGLE_QUOTE_TOKENIZER;
        Parser<?> wordTokenizer = Scanners.notAmong("[]").many().source();
        Parser<?> tokenizer = Parsers.or(operators.tokenizer(), singleQuoteTokenizer, wordTokenizer);

        // ---

        Parser<String> wordParser = Parsers.tokenType(String.class, "string");

        Parser<DslTag> tagParser = wordParser
                .between(operators.token("["), operators.token("]"))
                .map(DslTag::new);

        Parser<DslQuery> queryParser = tagParser
                .sepBy(operators.token(" ").many())
                .map(tags -> {
                    DslQuery query = new DslQuery();
                    query.setTags(tags);
                    return query;
                });

        return queryParser.from(tokenizer, ignored.skipMany());
    }
}
