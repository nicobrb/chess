package it.uniba.example.utils.test;

import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import it.uniba.example.utils.math.MathUtils;

/**
 * This class is for demo purpose only and must be deleted. It is meant to show
 * you how to create your own JUnit 5 test cases for your code.
 */
@Tag("util")
public class MathUtilsTest {

	private static MathUtils mu = null;

	@BeforeAll
	static void setUpAll() {
		System.out.println("setUpAll");
		mu = new MathUtils();
	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("teadDownAll");
		mu = null;
	}
	
	@BeforeEach
	void setUp() {
		System.out.println("setUpEach");
	}
	
	@AfterEach
	void tearDown() {
		System.out.println("teadDownEach");
	}

	@Test
	@DisplayName("Testing sum of integers")
	public void testAddition() {
		assumeNotNull(mu);
		assertEquals(3, mu.add(1, 2));
		assertEquals(-2, mu.add(-5, 3));
		assertSame(3, mu.add(1, 2));
		Integer val1 = new Integer(3);
		Integer val2 = mu.add(1, 2);
		assertTrue(val1.equals(val2));
		assertNotSame(val1, val2);
	}

	@Test
	@DisplayName("Testing division between integers")
	public void testDivision() {
		float res = mu.divide(16, 4);
		assertTrue(4 == res);
		assertFalse(4 == mu.divide(9, 3));
		assertThrows(ArithmeticException.class, () -> {
			mu.divide(7, 0);
		});
	}

}
