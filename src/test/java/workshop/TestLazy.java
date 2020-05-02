package workshop;

import org.junit.jupiter.api.Test;
import workshop.books.Author;
import workshop.books.Book;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestLazy {

	@Test
	void get() {
		String text = "hello, world!";
		Supplier<String> textSupplier = () -> text;
		Lazy<String> lazyText = new Lazy<>(textSupplier);
		assertEquals(text, lazyText.get());
	}

	@Test
	void transformationSequence() {
		String text = "hello, world!";
		Supplier<String> textSupplier = () -> text;
		Lazy<Integer> lazyTextLength = new Lazy<>(textSupplier)
				.map(String::length);

		assertEquals(text.length(), lazyTextLength.get());
	}

	@Test
	void proofOfLaziness() {
		Supplier<String> throwsException = () -> {throw new RuntimeException();};
		Lazy<String> unusedlazyText = new Lazy<>(throwsException); // supplier never called, as the value never used
	}
}