# Spring Boot Test Context Cache Limiter

This project gives you the ability to fail a test suite when a specific number of Spring Boot Contexts have been created.

## Why?

It has never been easier to write Spring Boot integration tests!

Using `@SpringBootTest` in a base class is one of the most common ways to ensure your tests cover the whole spectrum of your application.

It is not always easy to exclude beans that should not be used during the test (like any beans managing third-party API connections or costly I/O services).
More and more developers opt to use `@MockBean` and `@SpyBean` and evolve within the common context handled by `@SpringBootTest` to ensure their code work as expected in semi-isolation.
Unfortunately, dirtying the context this way often makes the context unique and prevent that unique context to be reused from the context cache.
Every context that is unique requires a whole context recreation.
This is usually not a big deal on smaller projects when a context gets created in a second. It may be problematic however as your project grows and when you have hundreds of context recreation that each take multiple seconds.

This module gives a way to specify a limit to the number of contexts that can be created so that a test build may explicitly fail when that number is reached, forcing the developer to find a more suitable and non-dirtying alternative and thus ensure that your build time remains acceptable.


## Usage

Spring Boot Test Context Cache Limiter works on any Spring Boot 2 version.
It is available on central maven:
```
<dependency>
  <groupId>com.teketik</groupId>
  <artifactId>spring-test-context-cache-limiter</artifactId>
  <version>boot2-v1.2</version>
  <scope>test</scope>
</dependency>
```

Once included in your maven project as a `test` dependency, it will by default automatically log the number of contexts created (under logger `logging.level.com.teketik.test=DEBUG`) with log message `Number of contexts: XX (no limit enforced)`.

Use the environment variable `spring.test.context.limitSize` to specify the maximum number of contexts that should be created during all your tests. Your build will fail if the number of contexts created exceeds this value.

See [spring-test-context-cache-limiter-example](spring-test-context-cache-limiter-example) for a concrete example


