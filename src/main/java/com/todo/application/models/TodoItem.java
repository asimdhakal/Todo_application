package com.todo.application.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "todo_item")
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotBlank(message = "Description is required")
    private String description;

    @Getter
    @Setter
    private boolean complete;


    public TodoItem() {}

    public TodoItem(String description) {
        this.description = description;
        this.complete = false;
    }
    
    @Override
    public String toString() {
        return String.format("TodoItem{id=%d, description='%s', complete='%s'}",
        id, description, complete);
    }
}
