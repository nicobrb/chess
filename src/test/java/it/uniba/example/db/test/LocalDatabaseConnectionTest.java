package it.uniba.example.db.test;

import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import it.uniba.example.db.DatabaseConnection;

@Tag("db")
public class LocalDatabaseConnectionTest {

	private static DatabaseConnection connection;

	@BeforeAll
	static void setUpAll() {
		connection = new DatabaseConnection("localhost", 46000);
		assumeTrue(connection.isConnected());
	}

	@AfterAll
	static void tearDownAll() {
		connection.disconnect();
		assumeFalse(connection.isConnected() == true);
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
	@Disabled
	public void testSomething() {
		fail("unimplemented");
	}

}
