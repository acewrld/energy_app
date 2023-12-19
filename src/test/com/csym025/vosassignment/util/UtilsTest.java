package com.csym025.vosassignment.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.*;

public class UtilsTest {

    @Test
    public void testAuthenticateCorrectEmailAddressIc() throws CustomException {
        boolean b = Utils.authenticateEmailAddress("richardadeyemi42@gmail.com");
        assertTrue(b);
    }

    @Test
    public void testAuthenticateCorrectMobileNo() throws CustomException {
        boolean b = Utils.authenticateMobileNo("07875805634");
        assertTrue(b);
    }

    @Test
    public void testAuthenticateInCorrectEmailAddressIc() throws CustomException {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> Utils.authenticateEmailAddress("abc")
        );

        String expectedMessage = "No match found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testAuthenticateInCorrectMobileNo() throws CustomException {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> Utils.authenticateMobileNo("123")
        );

        String expectedMessage = "No match found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}