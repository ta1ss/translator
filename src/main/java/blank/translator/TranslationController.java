package blank.translator;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.deepl.api.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class TranslationController {

    private final TranslationService translationService;

    @Autowired
    public TranslationController(TranslationService translationService) {
        this.translationService = translationService;
    }

    @GetMapping("/history")
    public List<Translation> getTranslationHistory(@RequestParam String sessionID) {
        return translationService.getTranslationHistoryBySessionID(sessionID);
    }

    @PostMapping("/translate")
    public Translation translateText(
            @RequestBody TranslationRequest request,
            @CookieValue(value = "sessionID", defaultValue = "") String sessionID,
            HttpServletRequest httpRequest) {
        String text = request.getText();
        String fromLanguage = request.getFromLanguage();
        String toLanguage = request.getToLanguage();

        String translatedText = translateWithDeepL(text, fromLanguage, toLanguage);

        Translation translation = new Translation();
        translation.setOriginalText(text);
        translation.setSessionID(sessionID);
        translation.setFromLanguage(fromLanguage);
        translation.setToLanguage(toLanguage);
        translation.setTranslatedText(translatedText);
        translation.setTimestamp(LocalDateTime.now());

        return translationService.saveTranslation(translation);
    }

    @DeleteMapping("/history/clear")
    public ResponseEntity<String> clearTranslationHistory(
            @CookieValue(value = "sessionID", defaultValue = "") String sessionID) {
        try {
            translationService.clearTranslationHistory(sessionID);

            return ResponseEntity.ok("Translation history cleared successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to clear translation history.");
        }
    }

    private String translateWithDeepL(String text, String fromLanguage, String toLanguage) {
        String authKey = "37929a27-4d1d-3454-60a2-47def362fdd3:fx"; // normally would be env variable

        try {
            Translator translator = new Translator(authKey);
            TextResult result = translator.translateText(text, fromLanguage, toLanguage);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Translation failed";
        }
    }
}