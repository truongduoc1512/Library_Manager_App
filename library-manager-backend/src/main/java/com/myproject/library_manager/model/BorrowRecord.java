package com.myproject.library_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.myproject.library_manager.model.enums.BorrowStatus;

@Entity
@Table(name = "borrow_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người mượn

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book; // Sách mượn

    private LocalDate borrowDate;  // Ngày mượn

    private LocalDate dueDate;     // Ngày phải trả

    private LocalDate returnDate;  // Ngày trả thực tế

    @Enumerated(EnumType.STRING)
    private BorrowStatus status;   // BORROWED / RETURNED / LATE
}
