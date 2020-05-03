package workshop;

import org.junit.jupiter.api.Test;
import workshop.books.Author;
import workshop.books.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSequence {

	@Test
	void constructor() {
		Collection<Integer> numbers = Arrays.asList(1);
		Sequence<Integer> numberSequence = new Sequence<>(numbers);
		assertEquals(numbers.size(), numberSequence.get().size());
		assertTrue(numberSequence.get().contains(1));
	}

	@Test
	void transformations() {
		String johnDoe = "John Doe";
		Book book = setupBook(johnDoe,"Some Title");

		Collection<Book> books = Arrays.asList(book);
		Collection<String> authorNamesUppercase = new Sequence<>(books)
				.map(Book::getAuthor)
				.map(Author::getName)
				.map(String::toUpperCase)
				.get();

		assertEquals(books.size(), authorNamesUppercase.size());
		assertTrue(authorNamesUppercase.contains(johnDoe.toUpperCase()));
	}

	@Test
	void complexTransformations() {
		Collection<String> days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thirsday", "Friday", "Saturday", "Sunday");

		Collection<Integer> numbers = new Sequence<>(days)
				.filter(name -> !name.startsWith("T"))
				.map(String::length)
			    .distinct()
				.get();

		assertEquals(3, numbers.size());
		assertTrue(numbers.contains(6));
		assertTrue(numbers.contains(8));
		assertTrue(numbers.contains(9));
	}

	@Test
	void complexTransformationsNoSequence() {
		Collection<String> days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

		Collection<Integer> numbers = new HashSet<>();

		for (String dayName: days) {
			if (!dayName.startsWith("T")) {
				Integer length = dayName.length();
				numbers.add(length);
			}
		}

		assertEquals(3, numbers.size());
		assertTrue(numbers.contains(6));
		assertTrue(numbers.contains(8));
		assertTrue(numbers.contains(9));
	}

	private Book setupBook(String authorName, String title) {
		Author author = new Author(authorName);
		Book book = new Book(title, author);
		return book;
	}
}