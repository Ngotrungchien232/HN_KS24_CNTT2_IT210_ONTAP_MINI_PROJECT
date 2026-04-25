package com.mini_project.model;



import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nội dung không được để trống!")
    @Column(nullable = false)
    private String content;

    @FutureOrPresent(message = "Ngày hết hạn phải là hôm nay hoặc trong tương lai!")
    private LocalDate dueDate;

    private String status;   // VD: "TODO", "DONE"

    private String priority; // VD: "LOW", "MEDIUM", "HIGH"
}
