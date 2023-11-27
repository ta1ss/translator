package blank.translator;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "translations")
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class Translation {
    @Id
    private String id; 
    private String sessionID;
    private String fromLanguage;
    private String toLanguage;
    private LocalDateTime timestamp;
    private String originalText;
    private String translatedText;

}