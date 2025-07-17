package org.pati.polish_to_utf_encoding.json_converter_logic;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;


@RestController
@RequestMapping("/json")
public class JsonFileController {

    @GetMapping(value = "/hi")
    public void sayHi() {
        System.out.println("hi, the rest service is working...");
    }

}

