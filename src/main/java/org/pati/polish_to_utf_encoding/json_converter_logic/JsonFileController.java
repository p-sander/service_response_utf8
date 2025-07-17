package org.pati.polish_to_utf_encoding.json_converter_logic;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;


@RestController
@RequestMapping("/json")
public class JsonFileController {

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<String> receiveJson(@RequestBody byte[] jsonFile) {

        String jsonFileInIso = new String(jsonFile, Charset.forName("ISO-8859-2"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonFileInIso);
    }

    @GetMapping(value = "/hi")
    public void sayHi() {
        System.out.println("hi, the rest service is working...");
    }

}

