package org.pati.polish_to_utf_encoding.soap_converter_logic;

import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlRequest;
import org.pati.polish_to_utf_encoding.soap_converter_logic.conversion.ConvertXmlResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class XmlConversionEndpoint {

    private static final String NAMESPACE_URI = "http://www.example.com/soap/conversion";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ConvertXmlRequest")
    @ResponsePayload
    public ConvertXmlResponse convertXmlEncoding(@RequestPayload ConvertXmlRequest request) {
        return new ConvertXmlResponse();
    }
}
