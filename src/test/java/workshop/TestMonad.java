package workshop;

import org.junit.jupiter.api.Test;
import workshop.books.Author;
import workshop.books.Book;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMonad {

	@Test
	void monadOfString() {
		String text = "hello, world!";
		Monad<String> monad = new Monad<>(text);
		assertEquals(text, monad.get());
	}

	@Test
	void emptyMonad() {
		String text = null;
		Monad<String> monad = new Monad<>(text);
		assertEquals(text, monad.get());
	}

	@Test
	void noopTransformation() {
		Monad<Integer> monad = new Monad<>(1);
		Monad<Integer> equalMonad = monad.map(Function.identity());
		assertEquals(monad.get(), equalMonad.get());
	}

	@Test
	void simpleTransformation() {
		String text = "hello, world!";
		Monad<String> textMonad = new Monad<>(text);
		Monad<Integer> lenghtOfTextMonad = textMonad.map(String::length);
		assertEquals(text.length(), lenghtOfTextMonad.get());
	}

	@Test
	void severalTransformations() {
		String testName = "John Doe";

		Monad<Book> book = new Monad<>(setupBook(testName, "Some Title"));
		Monad<Author> author = book.map(Book::getAuthor);
		Monad<String> authorName = author.map(Author::getName);

		assertEquals(testName, authorName.get());
	}

	@Test
	void transformationSequence() {
		String testName = "John Doe";

		String authorName = new Monad<>(setupBook(testName, "Some Title"))
				.map(Book::getAuthor)
				.map(Author::getName)
				.get();

		assertEquals(testName, authorName);
	}

	private Book setupBook(String authorName, String title) {
		Author author = new Author(authorName);
		Book book = new Book(title, author);
		return book;
	}
}