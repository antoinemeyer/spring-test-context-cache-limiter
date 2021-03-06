package com.teketik.test.example;


import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DelegatingService {

    @Resource
    private Service1 service1;

    @Resource
    private Service2 service2;

}