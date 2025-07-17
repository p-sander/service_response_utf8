package org.pati.polish_to_utf_encoding.soap_converter_logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlRequest;
import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class XmlConversionEndpointTest {

    @LocalServerPort
    private int port;

    private WebServiceTemplate webServiceTemplate;
    private static final Charset ISO_8859_2 = Charset.forName("ISO-8859-2");

    @BeforeEach
    void setUp() {
        String uri = "http://localhost:" + port + "/ws";

        // Konfigurujemy JAXB marshaller
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("org.pati.polish_to_utf_encoding.soap_converter_logic.conversion");

        // Inicjalizujemy WebServiceTemplate z marshallerem
        webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);
        webServiceTemplate.setDefaultUri(uri);
    }

    @Test
    void shouldConvertXmlFromIsoToUtf8() {
        // given
        String testXmlContent = "<dane>Zażółć gęślą jaźń</dane>";
        byte[] isoEncodedBytes = testXmlContent.getBytes(ISO_8859_2);

        ConvertXmlRequest request = new ConvertXmlRequest();
        request.setOriginalEncoding("ISO-8859-2");
        request.setXmlContent(isoEncodedBytes);  // zostanie automatycznie zakodowane do base64

        // when
        Object responseObj = webServiceTemplate.marshalSendAndReceive(request);
        assertNotNull(responseObj);
        ConvertXmlResponse response = (ConvertXmlResponse) responseObj;

        // then
        byte[] convertedBytes = response.getConvertedXmlContent();
        String result = new String(convertedBytes, StandardCharsets.UTF_8);

        assertEquals(testXmlContent, result);
    }
}
