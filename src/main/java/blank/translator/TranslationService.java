package blank.translator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    private final TranslationRepository translationRepository;

    @Autowired
    public TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public Translation saveTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    public List<Translation> getTranslationHistoryBySessionID(String sessionID) {
        return translationRepository.findBySessionID(sessionID);
    }

    public void clearTranslationHistory(String sessionID) {
        List<Translation> translationsToDelete = translationRepository.findBySessionID(sessionID);
        translationRepository.deleteAll(translationsToDelete);
    }
}
