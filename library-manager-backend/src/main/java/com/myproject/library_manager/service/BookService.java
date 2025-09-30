package com.myproject.library_manager.service;

import com.myproject.library_manager.model.Book;
import com.myproject.library_manager.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // CRUD cơ bản
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setPublisher(bookDetails.getPublisher());
                    book.setIsbn(bookDetails.getIsbn());
                    book.setCategory(bookDetails.getCategory());
                    book.setPublishDate(bookDetails.getPublishDate());
                    book.setTotalCopies(bookDetails.getTotalCopies());
                    book.setAvailableCopies(bookDetails.getAvailableCopies());
                    book.setLocation(bookDetails.getLocation());
                    return bookRepository.save(book);
                }).orElseThrow(() -> new RuntimeException("Book not found with id " + id));
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    // Các hàm tìm kiếm mở rộng
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<Book> searchByPublisher(String publisher) {
        return bookRepository.findByPublisherContainingIgnoreCase(publisher);
    }

    public List<Book> searchByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    public Book searchByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
