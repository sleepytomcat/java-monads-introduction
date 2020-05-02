package workshop;

import org.junit.jupiter.api.Test;
import workshop.books.Author;
import workshop.books.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMaybe {

	@Test
	void constructor() {
		String text = "hello, world!";
		Maybe<String> maybeText = new Maybe<>(text);
		assertEquals(text, maybeText.get());
	}

	@Test
	void getOrDefault() {
		Maybe<String> maybeText = new Maybe<>(null);

		String defaultText = "default text";
		assertEquals(defaultText, maybeText.getOrDefault(defaultText));
	}

	@Test
	void transformationSequenceNoMonad() {
		String johnDoe = "John Doe";
		Book book = setupBook(johnDoe, "Some Title");

		String authorName = "no name found";
		if (book != null) {
			Author author = book.getAuthor();
			if (author != null) {
				String name = author.getName();
				if (name != null) {
					authorName = name.toUpperCase();
				}
			}
		}

		assertEquals(johnDoe.toUpperCase(), authorName);
	}

	@Test
	void transformationSequenceWithMaybe() {
		String johnDoe = "John Doe";
		Book book = setupBook(johnDoe,"Some Title");

		String authorName = new Maybe<>(book)
				.map(Book::getAuthor)
				.map(Author::getName)
				.map(String::toUpperCase)
				.getOrDefault("no name found");

		assertEquals(johnDoe.toUpperCase(), authorName);
	}

	private Book setupBook(String authorName, String title) {
		Author author = new Author(authorName);
		Book book = new Book(title, author);
		return book;
	}
}