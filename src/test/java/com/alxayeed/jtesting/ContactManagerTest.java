package com.alxayeed.jtesting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.BooleanSupplier;

class ContactManagerTests {

	@Test
	public void shouldCreateContact() {
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
}
