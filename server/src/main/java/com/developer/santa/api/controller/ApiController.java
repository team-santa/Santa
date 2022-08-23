package com.developer.santa.api.controller;

import com.developer.santa.api.service.ApiService;
import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.local.LocalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;


    @GetMapping("/connect")
    public ResponseEntity<String> connect(@RequestParam String geomFilter, String crs, int page, int size) {
        return ResponseEntity.ok(apiService.connectApi(geomFilter, crs, page, size));
    }

    @GetMapping("/datacrawling")
    public void dataCrawling(@RequestParam String geomFilter, String crs, int page, int size, String localName) {
        apiService.dataCrawling(geomFilter, crs, page, size, localName);
    }


}
