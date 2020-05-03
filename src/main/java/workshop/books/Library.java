package workshop.books;

import java.util.HashMap;
import java.util.Map;

public class Library {
	public static class NotFoundException extends Exception {};

	private Map<Long, Book> _books = new HashMap<>();

	public Book getBook(long id) throws NotFoundException {
		if (_books.containsKey(id)) {
			return _books.get(id);
		} else {
			throw new NotFoundException();
		}
	}

	public void registerBook(long id, Book book) {
		_books.put(id, book);
	}
}
