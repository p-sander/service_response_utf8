package org.pati.polish_to_utf_encoding.soap_converter_logic.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class InvalidEncodingException extends RuntimeException {
    public InvalidEncodingException(String message) {
        super(message);
    }
}