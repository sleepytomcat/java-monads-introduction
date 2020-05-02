package workshop.books;

public class Book {
	private String _title;
	private Author _author;

	public Book(String title, Author author) {
		_title = title;
		_author = author;
	}

	public String getTitle() {
		return _title;
	}

	public Author getAuthor() {
		return _author;
	}
}
