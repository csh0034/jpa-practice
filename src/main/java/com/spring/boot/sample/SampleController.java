package com.spring.boot.sample;

import com.spring.boot.sample.validator.SampleValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;
    private final RestTemplate restTemplate;
    private final SampleValidator sampleValidator;
    private final HttpSession httpSession;

    @RequestMapping("/sample")
    public String sample(@RequestParam Map<String, String> param) {
        return param.toString();
    }

    @GetMapping("/sample2")
    public String sample2() {

        String returnValue = "";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("test1", "test1");
        parameters.add("test2", "test2");

        //Map<String, String> parameters = new HashMap<>();
        //parameters.put("test1", "test1");
        //parameters.put("test2", "test2");

        try {
            returnValue = restTemplate.postForObject("sadf", parameters, String.class);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return returnValue;
    }

    @RequestMapping("/web-query")
    public Map<String, String> webQuery(@RequestParam Map<String, String> param) {

        log.info("query : " + param);

        HashMap<String, String> map = new HashMap<String, String>() {
            private static final long serialVersionUID = 1511054834345342759L;
            {
            put("code", "success.");
            put("message", "IP is unauthorized");
        }};

        httpSession.setAttribute("map", "abc");
        return map;
    }


    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws IOException {
        Resource resource = new ClassPathResource("다운로드.png");
        File file = resource.getFile();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.valueOf(ContentDisposition.builder("attachement").filename(file.getName(), StandardCharsets.UTF_8).build()))
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
                .body(resource);

    }
}
