package org.pati.polish_to_utf_encoding.soap_converter_logic;

import lombok.extern.slf4j.Slf4j;
import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlRequest;
import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlResponse;
import org.pati.polish_to_utf_encoding.soap_converter_logic.exceptions.InvalidEncodingException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Endpoint
@Slf4j
public class XmlConversionEndpoint {
    private static final String NAMESPACE_URI = "http://www.example.com/soap/conversion";

    private static final String DEFAULT_ENCODING = "ISO-8859-2";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ConvertXmlRequest")
    @ResponsePayload
    public ConvertXmlResponse convertXmlEncoding(@RequestPayload ConvertXmlRequest request) {
        log.info("Received request with encoding: {}", request.getOriginalEncoding());

        byte[] base64DecodedBytes = request.getXmlContent();
        String originalFileEncoding = request.getOriginalEncoding();

        validateEncoding(originalFileEncoding, DEFAULT_ENCODING);

        String originalXmlString = new String(base64DecodedBytes, Charset.forName(DEFAULT_ENCODING));
        log.info("Decoded XML content:\n{}", originalXmlString);

        byte[] utf8Bytes = originalXmlString.getBytes(StandardCharsets.UTF_8);

        ConvertXmlResponse response = new ConvertXmlResponse();
        response.setConvertedXmlContent(utf8Bytes);

        log.info("Sending response encoded in UTF-8.");
        return response;
    }

    private void validateEncoding(String originalFileEncoding, String properEncoding) {
        if (originalFileEncoding == null || originalFileEncoding.isBlank()) {
            throw new InvalidEncodingException("Missing file encoding information.");
        }

        if (!originalFileEncoding.equalsIgnoreCase(properEncoding)) {
            throw new InvalidEncodingException(
                    "Invalid file encoding: expected " + properEncoding
                    + ", but received: " + originalFileEncoding);
        }

        log.info("Encoding is valid - {}", properEncoding);
    }

}
