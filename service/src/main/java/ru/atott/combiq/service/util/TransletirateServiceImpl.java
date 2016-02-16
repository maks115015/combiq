package ru.atott.combiq.service.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TransletirateServiceImpl implements TransletirateService {

    private static final String[] alphabet_rus = new String[] {
            "а","б","в","г","д","е","ё","ж","з","и","й","к","л","м","н","о","п",
            "р","с","т","у","ф","х","ц","ч","ш","щ","ъ","ы","ь","э","ю","я"," "
    };

    private static final String[] alphabet_eng = new String[] {
            "a","b","v","g","d","e","yo","zh","z","i","j","k","l","m","n","o","p",
            "r","s","t","u","f","h","c","ch","sh","shch","","y","","eh","yu","ya","-"
    };

    private static final Map<String, String> rusToEngMap = new HashMap<>();

    static {
        for(int i = 0; i < alphabet_rus.length; i++) {
            rusToEngMap.put(alphabet_rus[i], alphabet_eng[i]);
        }
    }

    @Override
    public String lowercaseAndTransletirate(String text, int maxLength) {
        if (text == null) {
            return null;
        }

        text = text.toLowerCase();

        StringBuilder result = new StringBuilder();
        String previousCharTransliteration = null;
        for (int i = 0; i < text.length(); i++) {
            char charAt = text.charAt(i);
            if ((charAt >= 'a' && charAt <= 'z')) {
                previousCharTransliteration = Character.toString(charAt);
                result.append(previousCharTransliteration);
            } else {
                if ('х' == charAt
                        && StringUtils.endsWithAny(previousCharTransliteration, new String[] { "c", "s", "e", "h" })) {
                    previousCharTransliteration = "kh";
                    result.append(previousCharTransliteration);
                } else {
                    String transletiration = rusToEngMap.get(Character.toString(charAt));
                    if (StringUtils.isNotBlank(transletiration)) {
                        previousCharTransliteration = transletiration;
                        result.append(transletiration);
                    }
                }
            }

            if (result.length() >= maxLength) {
                break;
            }
        }

        if (maxLength < text.length()) {
            return StringUtils.stripEnd(result.toString(), "-");
        }

        return result.toString();
    }

    @Override
    public String lowercaseAndTransletirate(String text) {
        return lowercaseAndTransletirate(text, Integer.MAX_VALUE);
    }
}
