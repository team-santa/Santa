package com.developer.santa.api.controller;

import com.developer.santa.api.domain.local.LocalDTO;
import com.developer.santa.api.domain.local.LocalRepository;
import com.developer.santa.api.service.LocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/local")
@RequiredArgsConstructor
public class LocalController {

    private final LocalService localService;

    @GetMapping("")
    public ResponseEntity<LocalDTO> getLocal(){
        LocalDTO local = localService.getLocal();
        return ResponseEntity.ok(local);
    }
}
