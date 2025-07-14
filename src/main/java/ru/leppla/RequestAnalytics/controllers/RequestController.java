package ru.leppla.RequestAnalytics.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.leppla.RequestAnalytics.dto.JsonRequestDTO;
import ru.leppla.RequestAnalytics.dto.TextRequestDTO;
import ru.leppla.RequestAnalytics.service.RequestService;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/request")
public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/text")
    public ResponseEntity<String> saveTextRequest(@RequestBody TextRequestDTO dto) {
        try {
            requestService.handleTextRequest(dto);
            return ResponseEntity.ok("Текстовый запрос сохранен.");
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Invalid URL");
        }
    }

    @PostMapping("/json")
    public ResponseEntity<String> saveJsonRequest(@RequestBody JsonRequestDTO dto) {
        requestService.handleJsonRequest(dto);
        return ResponseEntity.ok("JSON запрос сохранен.");
    }
}
