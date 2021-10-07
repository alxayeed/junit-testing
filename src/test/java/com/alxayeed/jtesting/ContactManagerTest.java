package com.alxayeed.jtesting;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTests {
	ContactManager contactManager;

	@BeforeEach
	public void setup(){
		contactManager = new ContactManager();
	}

	@AfterAll
	public void tearDownAll(){
		System.out.println("Clean up test data");
	}

	@Test
	@DisplayName("Test if contact is successfully created or not")
	public void testAddContact() {
		contactManager.addContact("Al", "Sayeed", "01683338978");
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals(1, contactManager.getAllContacts().size());
		Assertions.assertTrue(contactManager.getAllContacts().stream()
				.filter(contact -> contact.getFirstName().equals("Al") &&
						contact.getLastName().equals("Sayeed") &&
						contact.getPhoneNumber().equals("01683338978"))
				.findAny()
				.isPresent());
	}

	@Test
	@DisplayName("Test if the firstName is not null")
	public void testfirstNameNotNull(){
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact(null, "Sayeed", "01683338978");
		});
	}

	@Test
	@DisplayName("Test if the lastName is not null")
	public void testLastNameNotNull(){
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact("Al", null, "01683338978");
		});
	}

	@Test
	@DisplayName("Test if the phoneNumber is not null")
	public void testPhoneNumberNotNull(){
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact("Al", "Sayeed",null);
		});
	}

	@Test
	@DisplayName("Test Disable OS")
	@DisabledOnOs(value = OS.LINUX, disabledReason = "Test Disabled on Linux OS")
	public void testDisabledOs(){
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact("Al", "Sayeed",null);
		});
	}

	@Test
	@DisplayName("Test Enable OS")
	@EnabledOnOs(value = OS.MAC, disabledReason = "Test Only on Mac OS")
	public void testEnabledOs(){
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact("Al", "Sayeed",null);
		});
	}

	@Test
	@DisplayName("Create contact only on Dev system")
	public void shouldAddContactOnDev() {
		Assumptions.assumeTrue("TEST".equals(System.getProperty("ENV")));
		contactManager.addContact("Al", "Sayeed", "01683338978");
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals(1, contactManager.getAllContacts().size());
	}

	@DisplayName("Repeat a test")
	@RepeatedTest(value = 3, name = "ReapeatTest runs {currentRepetition} of {totalRepetitions} times")
	public void RepeatTest() {
		contactManager.addContact("Al", "Sayeed", "01683338978");
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals(1, contactManager.getAllContacts().size());
	}

	@DisplayName("Parameterized test with value source")
	@ParameterizedTest
	@ValueSource(strings = {"01612345678", "01712345678", "01812345678"})
	public void parameterizedValueSource(String phoneNumber) {
		contactManager.addContact("Al", "Sayeed", phoneNumber);
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals(1, contactManager.getAllContacts().size());
	}

	@DisplayName("Parameterized test with method source")
	@ParameterizedTest
	@MethodSource("getPhoneNumberList")
	public void parameterizedMethodSource(String phoneNumber) {
		contactManager.addContact("Al", "Sayeed", phoneNumber);
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals(1, contactManager.getAllContacts().size());
	}

	public List<String> getPhoneNumberList(){
		return Arrays.asList("01612345678", "01712345678", "01812345678");
	}

	@DisplayName("Parameterized test with CSV source")
	@ParameterizedTest
	@CsvSource({"01612345678", "01712345678", "01812345678"})
	public void parameterizedCsvSource(String phoneNumber) {
		contactManager.addContact("Al", "Sayeed", phoneNumber);
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals(1, contactManager.getAllContacts().size());
	}

	@DisplayName("Parameterized test with CSV File source")
	@ParameterizedTest
	@CsvFileSource(resources = "/data.csv")
	public void parameterizedCsvFileSource(String phoneNumber) {
		contactManager.addContact("Al", "Sayeed", phoneNumber);
		Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
		Assertions.assertEquals(1, contactManager.getAllContacts().size());
	}

}
