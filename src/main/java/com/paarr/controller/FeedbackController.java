package com.paarr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.paarr.dto.*;
import com.paarr.service.FeedbackService;

@RestController
@RequestMapping("/feedback")
@CrossOrigin("*")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO> save(@RequestBody FeedbackDTO dto) {
        return ResponseEntity.ok(feedbackService.save(dto));
    }

    @PostMapping("/list")
    public ResponseEntity<FeedbackPageDTO> list(@RequestBody FeedbackPageDTO dto) {
        return ResponseEntity.ok(feedbackService.list(dto));
    }

    @GetMapping("/get")
    public ResponseEntity<FeedbackDTO> get(@RequestParam Long id) {
        return ResponseEntity.ok(feedbackService.get(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> delete(@RequestParam Long id) {
        return ResponseEntity.ok(feedbackService.delete(id));
    }
}