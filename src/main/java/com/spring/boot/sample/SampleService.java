package com.spring.boot.sample;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

    private String name;

    public String getName() {
        return name;
    }
}
