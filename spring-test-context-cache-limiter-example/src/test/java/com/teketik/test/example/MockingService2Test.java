package com.teketik.test.example;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

public class MockingService2Test extends BaseTest {

    @Resource
    private DelegatingService delegatingService;

    @MockBean
    private Service2 service;

    @Test
    public void contextLoads() throws Exception {
        assertThat(delegatingService).isNotNull();
    }

}
