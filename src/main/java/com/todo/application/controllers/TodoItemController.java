package com.todo.application.controllers;

import com.todo.application.models.TodoItem;
import com.todo.application.repositories.TodoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.time.Instant;
import java.time.ZoneId;

@Controller
public class TodoItemController {
    private final Logger logger = LoggerFactory.getLogger(TodoItemController.class);

    @Autowired
    private TodoItemRepository todoItemRepository;

    @GetMapping("/")
    public ModelAndView index() {
        logger.info("request to GET index");
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("todoItems", todoItemRepository.findAll());
        modelAndView.addObject("today", Instant.now().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek());
        return modelAndView;
    }


    @PostMapping("/todo")
    public String createTodoItem(TodoItem todoItem, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-todo-item";
        }

        todoItemRepository.save(todoItem);
        return "redirect:/";
    }

    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") long id, TodoItem todoItem, BindingResult result, Model model) {
        try{
            if (todoItem.getDescription() != "") {
                if (result.hasErrors()) {
                    todoItem.setId(id);
                    return "update-todo-item";
                }

                todoItemRepository.save(todoItem);
                return "redirect:/";
            }
            return "";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while updating the todo item.");
            return "error-page";
        }
    }
}
