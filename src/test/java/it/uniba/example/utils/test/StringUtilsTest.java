package it.uniba.example.utils.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.uniba.example.utils.strings.StringUtils;

/**
 * This class is for demo purpose only and must be deleted. It is meant to show
 * you how to create your own JUnit 5 test cases for your code.
 */
public class StringUtilsTest {
	@Test
	@DisplayName("Convert to double pass")
	public void testConvertToDoubleOK() {
		// Test case with the age is a numeric string
		String age = "1990";
		Double expAge = Double.valueOf(age);
		Double actual = StringUtils.convertToDouble(age);

		assertAll("Do many assertions.", () -> {
			assertNotNull(actual);
			assertEquals(expAge, actual);
		});

		// Or Java 8 Lambdas style
		assertAll("Do many assertions.Java 8 Lambdas style", () -> {
			assertNotNull(actual, () -> "The actual is NULL");
			assertEquals(expAge, actual, () -> "The expected is: " + expAge + " while the actual is:" + actual);
		});

	}

	@Test
	public void testConvertToDoubleWithNullArgument() {
		// Test case with the age is null
		String age = null;
		Double actual = StringUtils.convertToDouble(age);
		assertNull(actual, () -> "The actual is not null");
	}

	@Test
	public void testConvertToDoubleThrowException() {
		// Test with the age is a non numeric string
		String age = "N/A";
		assertThrows(NumberFormatException.class, () -> {
			StringUtils.convertToDouble(age);
		});

		assertThrows(NumberFormatException.class, () -> {
			StringUtils.convertToDouble(age);
		});
	}

	@Test
	public void testIsNullOrBlankOK() {
		// Test the case that the input is NULL
		String input = null;

		assertTrue(StringUtils.isNullOrBlank(input));
		assertTrue(StringUtils.isNullOrBlank(input), () -> "The string is not null or blank");

		// Test case with the input is empty
		input = " ";
		assertTrue(StringUtils.isNullOrBlank(input));

		// Test case with the input is not empty
		input = "abc";

		assertFalse(StringUtils.isNullOrBlank(input));

	}

	@Test
	public void testConcatWithRegularInput() {
		String st1 = "Hello";
		String st2 = "World";
		String st3 = "!";
		String expect = st1 + st2 + st3;
		String actual = StringUtils.concat(st1, st2, st3);
		assertEquals(expect, actual);
	}

	@Test
	public void testConcatWithNullInput() {
		String st1 = "Hello";
		String st2 = "World";
		String st3 = null;
		String expect = st1 + st2;
		String actual = StringUtils.concat(st1, st2, st3);
		assertEquals(expect, actual);
	}

	@Disabled
	@Test
	public void testConcatWithAllNullInput() {
		String actual = StringUtils.concat();
		assertNull(actual);
	}

}
