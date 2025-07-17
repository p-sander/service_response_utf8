package org.pati.polish_to_utf_encoding.soap_converter_logic;

import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlRequest;
import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Endpoint
public class XmlConversionEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(XmlConversionEndpoint.class);
    private static final String NAMESPACE_URI = "http://www.example.com/soap/conversion";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ConvertXmlRequest")
    @ResponsePayload
    public ConvertXmlResponse convertXmlEncoding(@RequestPayload ConvertXmlRequest request) {
        logger.info("Received request with encoding: {}", request.getOriginalEncoding());

        byte[] base64DecodedBytes = request.getXmlContent();
        String originalEncoding = request.getOriginalEncoding();

        String originalXmlString = new String(base64DecodedBytes, Charset.forName(originalEncoding));
        logger.info("Decoded XML content:\n{}", originalXmlString);

        byte[] utf8Bytes = originalXmlString.getBytes(StandardCharsets.UTF_8);

        ConvertXmlResponse response = new ConvertXmlResponse();
        response.setConvertedXmlContent(utf8Bytes);

        logger.info("Sending response encoded in UTF-8.");
        return response;
    }
}
