package com.spring.boot.sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SampleControllerTest {

    Logger logger = LoggerFactory.getLogger(SampleControllerTest.class);
    
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ctx;

    @MockBean
    private SampleService sampleService;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void sample() throws Exception {
        when(sampleService.getName()).thenReturn("최승훈 mock");

        mockMvc.perform(get("/sample"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"q", "qwerasdfzxcv", "qq23"})
    void sample2(String key) {
        logger.info("key : {}", key);
    }

    @DisplayName("ParameterizedTest, MethodSource 테스트")
    @ParameterizedTest(name="{index} : {2}")
    @MethodSource("methodSourceTest")
    void sample3(String key, String value, String message) {
        logger.info("key : {}, value : {}", key, value);
    }

    @Disabled
    @Test
    void test() {
        logger.info("test");
    }

    private static Stream<Arguments> methodSourceTest() {
        return Stream.of(
                Arguments.of("key1", "value1", "서브테스트 1"),
                Arguments.of("key2", "value2", "서브테스트 2"),
                Arguments.of("key3", "value3", "서브테스트 3"),
                Arguments.of("key4", "value4", "서브테스트 4")
        );
    }
}