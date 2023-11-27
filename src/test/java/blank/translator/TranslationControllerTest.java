package blank.translator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class TranslationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTranslateEndpoint() throws Exception {
        String requestBody = "{\"text\":\"Hello\",\"fromLanguage\":\"en\",\"toLanguage\":\"es\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/translate")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.translatedText").exists());
    }

    @Test
    public void testGetHistoryEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/history")
                .param("sessionID", "exampleSessionID")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testClearHistoryEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/history/clear")
                .param("sessionID", "exampleSessionID")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
