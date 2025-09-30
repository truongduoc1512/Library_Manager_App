package com.myproject.library_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // DAILY / MONTHLY / YEARLY / CUSTOM

    private String generatedBy; // Admin nào tạo

    private String content; // JSON hoặc text mô tả thống kê

    private LocalDateTime createdAt;
}
