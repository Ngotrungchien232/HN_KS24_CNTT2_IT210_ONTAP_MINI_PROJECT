package com.mini_project.controller;
import com.mini_project.model.Todo;
import com.mini_project.repository.TodoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;


    @GetMapping("/")
    public String listTodos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        model.addAttribute("newTodo", new Todo());
        return "index";
    }


    @PostMapping("/add")
    public String addTodo(@Valid @ModelAttribute("newTodo") Todo todo,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todos", todoRepository.findAll());
            return "index";
        }
        if (todo.getStatus() == null || todo.getStatus().isBlank()) {
            todo.setStatus("TODO");
        }
        if (todo.getPriority() == null || todo.getPriority().isBlank()) {
            todo.setPriority("MEDIUM");
        }
        todoRepository.save(todo);
        return "redirect:/";
    }


    @GetMapping("/todos/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy task với ID: " + id));
        model.addAttribute("todo", todo);
        return "edit";
    }


    @PostMapping("/todos/update")
    public String updateTodo(@Valid @ModelAttribute("todo") Todo todo,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        todoRepository.save(todo); // ID có sẵn → JPA tự hiểu là UPDATE
        redirectAttributes.addFlashAttribute("message", " Cập nhật task thành công!");
        return "redirect:/";
    }


    @GetMapping("/todos/delete/{id}")
    public String deleteTodo(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        if (!todoRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("error", " Không tìm thấy task để xóa!");
            return "redirect:/";
        }
        todoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", " Xóa task thành công!");
        return "redirect:/";
    }
}