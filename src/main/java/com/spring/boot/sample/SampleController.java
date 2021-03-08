package com.spring.boot.sample;

import com.spring.boot.sample.validator.SampleValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;
    private final RestTemplateBuilder restTemplateBuilder;
    private final SampleValidator sampleValidator;

    private RestTemplate restTemplate() {
        return restTemplateBuilder
                .setReadTimeout(Duration.ofMillis(5000))
                .setConnectTimeout(Duration.ofMillis(5000))
                .additionalInterceptors((request, body, execution) -> {

                    URI uri = request.getURI();

                    StringBuilder reqLog = new StringBuilder();
                    reqLog.append("[REQ] ").append("Uri : ").append(request.getURI()).append(", Method : ").append(request.getMethod()).append(", Request Body : ")
                            .append(new String(body, StandardCharsets.UTF_8));
                    System.out.println(reqLog.toString());

                    ClientHttpResponse response = execution.execute(request, body);

                    StringBuilder resLog = new StringBuilder();
                    resLog.append("[RES] ").append("Uri : ").append(uri).append(", Status code : ").append(response.getStatusCode()).append(", Response Body : ")
                            .append(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
                    System.out.println(resLog.toString());

                    return response;
                })
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()))
                .build();
    }

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
            returnValue = restTemplate().postForObject("sadf", parameters, String.class);
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        return returnValue;
    }

    @RequestMapping("/web-query")
    public Map<String, String> webQuery(@RequestParam Map<String, String> param) {

        log.info("query : " + param);

        return new HashMap<String, String>() {{
            put("code", "success.");
            put("message", "IP is unauthorized");
        }};
    }

    @GetMapping("/valid")
    public SampleVO sampleVO(SampleVO sampleVO, BindingResult bindingResult) {

        if (ObjectUtils.isEmpty(sampleVO.getKey1())) {
            bindingResult.rejectValue("key1","key1.empty");
        }
        if (ObjectUtils.isEmpty(sampleVO.getEmail())) {
            bindingResult.rejectValue("email","email.empty");
        }

        if (bindingResult.hasErrors()) {
            // throw exception
        }

        return sampleVO;
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
