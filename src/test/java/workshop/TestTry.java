package workshop;

import org.junit.jupiter.api.Test;
import workshop.books.Author;
import workshop.books.Book;
import workshop.books.Library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestTry {
	@Test
	void value() throws Throwable {
		String text = "hello, world!";
		Try<String> tryString = new Try<>(text);
		assertEquals(text, tryString.get());
	}

	@Test
	void exception() {
		Throwable exception = new RuntimeException();
		Try<String> tryString = new Try<>(exception);
		assertThrows(
				RuntimeException.class,
				() -> tryString.get()
		);
	}

	@Test
	void transform() throws Throwable {
		String text = "hello, world!";
		Try<String> tryString = new Try<>(text)
				.map(String::toUpperCase);
		assertEquals(text.toUpperCase(), tryString.get());
	}

	@Test
	void recoverWithTryCatch() {
		Library library = setupLibraryMock();

		long bookId = -1; // wrong book id

		String result = null;
		try {
			Book book = library.getBook(bookId); // wrong book Id; will throw NotFoundException
			Author author = book.getAuthor();
			result = author.getName();
		} catch (Library.NotFoundException ex) {
			result = "BOOK NOT FOUND";
		} catch (RuntimeException ex) {
			result = "NO AUTHOR";
		} catch (Throwable ex) {
			result = "UNEXPECTED FAILURE";
			System.err.println(ex);
		}
		assertEquals("BOOK NOT FOUND", result);
	}

	@Test
	void recover() throws Throwable {
		Library library = setupLibraryMock();

		long bookId = -1; // wrong book id

		// business logic
		Try<String> tryString = new Try<>(bookId)
				.map(library::getBook) // wrong book Id; will throw NotFoundException
				.map(Book::getAuthor)
				.map(Author::getName);

		// recovering and logging
		String result = tryString
				.recover(Library.NotFoundException.class, ex -> "BOOK NOT FOUND")
				.recover(RuntimeException.class, ex -> "NO AUTHOR")
				.onFailure(Throwable.class, ex -> System.err.println(ex))
				.recover(Throwable.class, ex -> "UNEXPECTED FAILURE")
				.get();

		assertEquals("BOOK NOT FOUND", result);
	}

	Library setupLibraryMock() {
		String authorName = "John Doe";
		String title = "Some Title";
		Author author = new Author(authorName);
		Book book = new Book(title, author);
		Library library = new Library();
		library.registerBook(0, book);
		return library;
	}
}