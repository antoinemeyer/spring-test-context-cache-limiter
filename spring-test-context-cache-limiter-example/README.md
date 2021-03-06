This is a concrete example of how to use [spring-test-context-cache-limiter](https://github.com/antoinemeyer/spring-test-context-cache-limiter).

This module contains two very classic tests:
  - [MockingService1Test](https://github.com/antoinemeyer/spring-test-context-cache-limiter/blob/master/spring-test-context-cache-limiter-example/src/test/java/com/teketik/test/example/MockingService1Test.java)
  - [MockingService2Test](https://github.com/antoinemeyer/spring-test-context-cache-limiter/blob/master/spring-test-context-cache-limiter-example/src/test/java/com/teketik/test/example/MockingService2Test.java)

Those tests are quite similar and use a `@MockBean` on different spring beans which makes their context definition unique, and thus not cacheable.

Running `mvn clean install` will show you the two tests running successfully in two different contexts.

Both those tests are annotated with `@BootstrapWith(LimitingContextsBootstrapper.class)` but no limit is specified in the default maven profile and therefore no limit is enforced.

Running `mvn clean install -Dspring.test.context.limitSize=1` will result in a failed build as the maximum number of contexts allowed is set to 1.

The build will fail as expected with the following stacktrace:
```
[ERROR] Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.226 s <<< FAILURE! - in com.teketik.test.example.MockingService2Test
[ERROR] contextLoads  Time elapsed: 0 s  <<< ERROR!
java.lang.IllegalStateException: Failed to load ApplicationContext
Caused by: java.lang.IllegalStateException: Number of test contexts exceeds configured maximum: 1
```

-----

Note:
A profile `one-context-limit` has also been added in the pom.xml to show an example of this configuration enforced by maven.
Running `mvn clean install -Pone-context-limit` would result in a similar failured.
