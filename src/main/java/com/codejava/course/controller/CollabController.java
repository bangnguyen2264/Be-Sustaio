package com.codejava.course.controller;

import com.codejava.course.model.request.CollaborationFilterRequest;
import com.codejava.course.service.CollabService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/collab")
@RequiredArgsConstructor
public class CollabController {
    private final CollabService collabService;

    @GetMapping
    public ResponseEntity getAll(@Valid @ParameterObject CollaborationFilterRequest collaborationFilterRequest) {
        return ResponseEntity.ok(collabService.getAllCollabs(collaborationFilterRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(collabService.getCollabById(id));
    }

}
