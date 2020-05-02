package workshop.books;

import java.util.HashMap;
import java.util.Map;

public class Library {
	private Map<Long, Book> _books = new HashMap<>();

	public Book getBook(long id) {
		return _books.get(id);
	}

	public void registerBook(long id, Book book) {
		_books.put(id, book);
	}
}
