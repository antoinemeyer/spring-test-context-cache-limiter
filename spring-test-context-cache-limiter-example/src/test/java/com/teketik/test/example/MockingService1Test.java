package com.teketik.test.example;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

@SpringBootTest(classes = Application.class)
public class MockingService1Test {

    @Resource
    private DelegatingService delegatingService;

    @MockBean
    private Service1 service;

    @Test
    public void contextLoads() throws Exception {
        assertThat(delegatingService).isNotNull();
    }
}