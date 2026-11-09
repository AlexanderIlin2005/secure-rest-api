package ru.sashil.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DataController {
    @GetMapping("/data")
    public List<String> getData() {
        return List.of("Secret Data 1", "Secret Data 2", "Secret Data 3");
    }
}