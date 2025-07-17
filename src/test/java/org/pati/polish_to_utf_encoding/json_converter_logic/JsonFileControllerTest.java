package org.pati.polish_to_utf_encoding.json_converter_logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JsonFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Charset ISO_8859_2 = Charset.forName("ISO-8859-2");

    @Test
    void shouldReceiveIsoStreamAndReturnUtf8Json() throws Exception {
        // given
        String testJson = "{\"wiadomość\": \"Zażółć gęślą jaźń\"}";
        byte[] isoEncodedBytes = testJson.getBytes(ISO_8859_2);

        // when & then
        mockMvc.perform(post("/json/create")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .content(isoEncodedBytes))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(testJson));
    }
}
