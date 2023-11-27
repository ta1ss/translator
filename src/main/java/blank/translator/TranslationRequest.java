package blank.translator;

import lombok.Data;

@Data
public class TranslationRequest {
    private String text;
    private String fromLanguage;
    private String toLanguage;
}
