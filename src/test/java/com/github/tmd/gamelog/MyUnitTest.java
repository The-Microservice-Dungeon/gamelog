package com.github.tmd.gamelog;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This is the naming convention for unit tests
 * Unit tests are run using the maven surefire plugin.
 * Either by using the command line: mvn test -Psurefire
 * or clicking on the task surefire:test in the maven menu.
 * Make sure you configure your environment variables (Through maven runner or system)
 */
@Disabled
@SpringBootTest
public class MyUnitTest {
    @Test
    public void myTestFunction(){
        assert true;
    }
}
