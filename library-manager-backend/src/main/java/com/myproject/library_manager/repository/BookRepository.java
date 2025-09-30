package com.myproject.library_manager.repository;

import com.myproject.library_manager.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // Tìm sách theo tiêu đề (chứa từ khóa)
    List<Book> findByTitleContainingIgnoreCase(String title);

    // Tìm sách theo tác giả
    List<Book> findByAuthorContainingIgnoreCase(String author);

    // Tìm sách theo nhà xuất bản
    List<Book> findByPublisherContainingIgnoreCase(String publisher);

    // Tìm sách theo thể loại
    List<Book> findByCategoryIgnoreCase(String category);

    // Tìm sách theo ISBN (chính xác)
    Book findByIsbn(String isbn);
}
