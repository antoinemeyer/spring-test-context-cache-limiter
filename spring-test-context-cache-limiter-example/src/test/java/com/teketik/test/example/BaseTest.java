package com.teketik.test.example;

import com.teketik.test.LimitingContextsBootstrapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.BootstrapWith;

@BootstrapWith(LimitingContextsBootstrapper.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseTest {

}
