package junit_testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit_testing.AStarTest;

@RunWith(Suite.class)
@SuiteClasses({ AStarTest.class })
public class AllTests {

}
