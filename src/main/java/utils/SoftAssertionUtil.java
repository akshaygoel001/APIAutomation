package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {
    private static SoftAssert softAssertInstance;

    private SoftAssertionUtil(){}

//    public SoftAssertionUtil() {
//        softAssert = new SoftAssert();
//    }

    private static SoftAssert getSoftAssertInstance(){
        if (softAssertInstance == null){
            softAssertInstance = new SoftAssert();
        }
        return softAssertInstance;
    }

    public static void assertTrue(boolean condition, String message) {
        try {
            getSoftAssertInstance().assertTrue(condition, message);
        } catch (AssertionError e) {
            getSoftAssertInstance().fail(message);
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            getSoftAssertInstance().assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            getSoftAssertInstance().fail(message);
        }
    }

    public static void assertNotEquals(Object actual, Object expected, String message) {
        try {
            getSoftAssertInstance().assertNotEquals(actual, expected, message);
        } catch (AssertionError e) {
            getSoftAssertInstance().fail(message);
        }
    }

    public static void assertAll() {
        getSoftAssertInstance().assertAll();
    }
}

//
//softAssertionUtil.assertTrue(page.CURATION_HEADER.isPresent(),
//       " Header should be present");
//               softAssertionUtil.assertTrue(page.CURATION_MESSAGE.isPresent(),
//       " Message should be present");
//               softAssertionUtil.assertAll();
