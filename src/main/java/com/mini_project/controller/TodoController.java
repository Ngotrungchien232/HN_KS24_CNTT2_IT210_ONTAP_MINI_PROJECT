package com.mini_project.controller;


import com.mini_project.model.Todo;
import com.mini_project.repository.TodoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    //  GET / → Hiển thị danh sách + form thêm mới
    @GetMapping
    public String listTodos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        model.addAttribute("newTodo", new Todo()); // object binding cho form
        return "index"; // → templates/index.html
    }

    //  POST /add → Xử lý thêm mới với Validation
    @PostMapping("/add")
    public String addTodo(@Valid @ModelAttribute("newTodo") Todo todo,
                          BindingResult bindingResult,
                          Model model) {

        // Nếu có lỗi validation → quay lại form, hiển thị lỗi
        if (bindingResult.hasErrors()) {
            model.addAttribute("todos", todoRepository.findAll());
            return "index"; // trả về view, KHÔNG redirect
        }

        // Gán giá trị mặc định nếu chưa có
        if (todo.getStatus() == null || todo.getStatus().isBlank()) {
            todo.setStatus("TODO");
        }
        if (todo.getPriority() == null || todo.getPriority().isBlank()) {
            todo.setPriority("MEDIUM");
        }

        todoRepository.save(todo);
        return "redirect:/"; //  PRG Pattern – tránh submit lại khi F5
    }
}
