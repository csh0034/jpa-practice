package study.rawjpa.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder
                .setReadTimeout(Duration.ofMillis(5000))
                .setConnectTimeout(Duration.ofMillis(5000))
                .additionalInterceptors((request, body, execution) -> {

                    URI uri = request.getURI();

                    String reqLog = "[REQ] " + "Uri : " + request.getURI() + ", Method : " + request.getMethod() + ", Request Body : " +
                            new String(body, StandardCharsets.UTF_8);
                    System.out.println(reqLog);

                    ClientHttpResponse response = execution.execute(request, body);

                    String resLog = "[RES] " + "Uri : " + uri + ", Status code : " + response.getStatusCode() + ", Response Body : " +
                            StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);
                    System.out.println(resLog);

                    return response;
                })
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()))
                .build();
    }
}