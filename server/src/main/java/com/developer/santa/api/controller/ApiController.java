package com.developer.santa.api.controller;

import com.developer.santa.api.service.ApiService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/connect")
    public Mono<String> connect(@RequestParam String geomFilter, String crs, int page, int size, String localName) {
        return apiService.connectApi(geomFilter, crs, page, size, localName);
    }

    @GetMapping("/datacrawling")
    public void dataCrawling(
            @RequestParam String geomFilter, String crs, int page, int size, String localName
    ) {
        apiService.dataCrawling(geomFilter, crs, page, size, localName);
    }


}
