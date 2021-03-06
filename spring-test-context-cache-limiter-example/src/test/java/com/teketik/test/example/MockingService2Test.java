package com.teketik.test.example;


import static org.assertj.core.api.Assertions.assertThat;

import com.teketik.test.LimitingContextsBootstrapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.BootstrapWith;

import javax.annotation.Resource;

@BootstrapWith(LimitingContextsBootstrapper.class)
@SpringBootTest(classes = Application.class)
public class MockingService2Test {

    @Resource
    private DelegatingService delegatingService;

    @MockBean
    private Service2 service;

    @Test
    public void contextLoads() throws Exception {
        assertThat(delegatingService).isNotNull();
    }

}
