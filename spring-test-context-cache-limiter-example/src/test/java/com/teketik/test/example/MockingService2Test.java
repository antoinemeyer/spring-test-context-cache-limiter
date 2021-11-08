package com.teketik.test.example;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

@SpringBootTest(classes = Application.class)
public class MockingService2Test {

    @Resource
    private DelegatingService delegatingService;

    @MockBean
    private Service2 service;

    @Test
    public void contextLoads1() throws Exception {
        assertThat(delegatingService).isNotNull();
    }

    @Test
    public void contextLoads2() throws Exception {
        assertThat(delegatingService).isNotNull();
    }

}
