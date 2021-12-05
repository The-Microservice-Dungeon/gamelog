package com.github.tmd.gamelog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This is the naming convention for integration tests
 * Unit tests are run using the maven failsafe plugin.
 * Either by using the command line: mvn test -Pfailsafe
 * or clicking on the task failsafe:integration-test in the maven menu.
 * Make sure you configure your environment variables (Through maven runner or system)
 */
@Disabled
@SpringBootTest
public class MyIntegrationTest {
    @Test
    public void myTestFunction(){
        assert true;
    }
}
