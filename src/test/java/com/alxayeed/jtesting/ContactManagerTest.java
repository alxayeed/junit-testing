package com.alxayeed.jtesting;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.BooleanSupplier;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTests {
	ContactManager contactManager;

	@BeforeEach
	public void setup(){
		contactManager = new ContactManager();
	}

	@Test
	@DisplayName("Test if contact is successfully created or not")
	public void testAddContact() {
		ContactManager contactManager = new ContactManager();
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
		ContactManager contactManager = new ContactManager();
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact(null, "Sayeed", "01683338978");
		});
	}

	@Test
	@DisplayName("Test if the lastName is not null")
	public void testLastNameNotNull(){
		ContactManager contactManager = new ContactManager();
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact("Al", null, "01683338978");
		});
	}

	@Test
	@DisplayName("Test if the phoneNumber is not null")
	public void testPhoneNumberNotNull(){
		ContactManager contactManager = new ContactManager();
		Assertions.assertThrows(RuntimeException.class, ()->{
			contactManager.addContact("Al", "Sayeed",null);
		});
	}

	@AfterEach
	public void tearDown(){
		System.out.println("Invoked after each method");  
	}

	@AfterAll
	public void tearDownAll(){
		System.out.println("Invoked after All methods");
	}

}
