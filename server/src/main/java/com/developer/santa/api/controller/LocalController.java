package com.developer.santa.api.controller;

import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.local.LocalDTO;

import com.developer.santa.api.service.LocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/local")
@RequiredArgsConstructor
public class LocalController {

    private final LocalService localService;

    //todo page
    @GetMapping("")
    public ResponseEntity<List<LocalDTO>> getLocal(){
        return ResponseEntity.ok(localService.getLocal());
    }

    @GetMapping("/{local}")
    public ResponseEntity<Local> getDetailLocal(@PathVariable String local){
        return ResponseEntity.ok(localService.getDetailLocal(local));
    }
}
