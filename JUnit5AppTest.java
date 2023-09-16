package com.makotojava.learn.hellojunit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@DisplayName("Testing App using JUnit 5")
public class JUnit5AppTest {

  private static final Logger logger = LoggerFactory.getLogger(JUnit5AppTest.class);

  private App testApp;

  public static void init() {
    logger.info("Run before any test is run in the class");
  }

  public static void done() {
    logger.info("Run after any test is ran in the class");
  }

  @BeforeEach
  public void setUp() {
    this.testApp = new App();
  }

  @AfterEach
  public void tearDown() {
    this.testApp = null;
  }

  // Disabled test
  @Test
  @Disabled
  @DisplayName("Test should not run")
  void testNotRun() {
    logger.info("This test will not run.");
  }

  @Test
  @DisplayName("Test nums that are positive")
  public void testAdd() {
    Assertions.assertNotNull(testApp);
    Assertions.assertAll("add",
            () -> {
              long[] numsToSum = {1, 2, 3, 4};
              long expectedSum = 10;
              Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
            },
            () -> {
              long[] numsToSum = {20, 934, 110};
              long expectedSum = 1064;
              Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
            },
            () -> {
              long[] numsToSum = {2, 4, 6};
              long expectedSum = 12;
              Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
            });
  }

  @Nested
  @DisplayName("Test nums that are negative")
  class NegativeNumbersTest {

    private App testApp;

    @BeforeEach
    public void setUp() throws Exception {
      testApp = new App();
    }

    @AfterEach
    public void tearDown() throws Exception {
      testApp = null;
    }
    @Test
    @DisplayName("Three tests when nums are negative")
    public void testAdd() {
      Assertions.assertNotNull(testApp);
      Assertions.assertAll("add",
              () -> {
                long[] numsToSum = {-1, -2, -3, -4};
                long expectedSum = -10;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              },
              () -> {
                long[] numsToSum = {-20, -934, -110};
                long expectedSum = -1064;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              },
              () -> {
                long[] numsToSum = {-2, -4, -6};
                long expectedSum = -12;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              });
    }
  }

  @Nested
  @DisplayName("Test when nums are positive and negative")
  class PositiveAndNegativeNumbersTest {

    @Test
    @DisplayName("Three tests when nums are positive and negative")
    public void testAdd() {
      Assertions.assertNotNull(testApp);
      Assertions.assertAll("add",
              () -> {
                long[] numsToSum = {1, 2, -3, 4};
                long expectedSum = 4;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              },
              () -> {
                long[] numsToSum = {20, 934, -110};
                long expectedSum = 844;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              },
              () -> {
                long[] numsToSum = {2, -4, 6};
                long expectedSum = 4;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              });
    }

    @Test
    @DisplayName("Test on only Fridays")
    public void testAdd_OnlyOnFriday() {
      Assertions.assertNotNull(testApp);
      LocalDateTime localDateTime = LocalDateTime.now();
      Assumptions.assumeTrue(localDateTime.getDayOfWeek().getValue() == 5);
      long[] numsToSum = {1, 2, 3, 4, 5};
      long expectedSum = 15;
      Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
    }

    @Test
    @DisplayName("Test on only Fridays with lambda implementation")
    public void testAdd_OnlyOnFriday_WithLambda() {
      Assertions.assertNotNull(testApp);
      LocalDateTime localDateTime = LocalDateTime.now();
      Assumptions.assumingThat(localDateTime.getDayOfWeek().getValue() == 5,
              () -> {
                long[] numsToSum = { 1, 2, 3, 4, 5 };
                long expectedSum = 15;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              });
    }
  }

  @Nested
  @DisplayName("Test with single num")
  class JUnit5AppSingleOperandTest {

    @Test
    @DisplayName("Two tests when there is only one num and positive")
    public void testAdd_NumbersGt0() {
      Assertions.assertNotNull(testApp);
      Assertions.assertAll("add",
              () -> {
                long[] numsToSum = {1};
                long expectedSum = 1;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              },
              () -> {
                long[] numsToSum = {0};
                long expectedSum = 0;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              });
    }

    @Test
    @DisplayName("Two tests when there is only one num and negative")
    public void testAdd_NumbersLt0() {
      Assertions.assertNotNull(testApp);
      Assertions.assertAll("add",
              () -> {
                long[] numsToSum = {-1};
                long expectedSum = -1;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              },
              () -> {
                long[] numsToSum = {-10};
                long expectedSum = -10;
                Assertions.assertEquals(expectedSum, testApp.add(numsToSum));
              });
    }
  }

  @Nested
  @DisplayName("Test when there are 0 nums")
  class JUnit5AppZeroOperandsTest {

    @Test
    @DisplayName("One test with empty argument")
    public void testAdd_ZeroOperands_EmptyArgument() {
      assertNotNull(testApp);
      long[] numsToSum = {};
      Assertions.assertThrows(IllegalArgumentException.class, () -> testApp.add(numsToSum));
    }

    @Test
    @DisplayName("One test with null argument")
    public void testAdd_ZeroOperands_NullArgument() {
      Assertions.assertNotNull(testApp);
      long[] numbersToSum = null;
      Throwable expectedException = assertThrows(IllegalArgumentException.class,
              () -> testApp.add(numbersToSum));
      assertEquals("Operands argument cannot be null", expectedException.getLocalizedMessage());
    }
  }
}
