package com.developer.santa.api.controller;


import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainDTO;
import com.developer.santa.api.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mountain")
@RequiredArgsConstructor
public class MountainController {

    private final MountainService mountainService;

    //todo page
    @GetMapping("")
    public ResponseEntity<List<MountainDTO>> getMountain(){
        return ResponseEntity.ok(mountainService.getMountain());
    }

    @GetMapping("/selection")
    public ResponseEntity<List<MountainDTO>> getSelectMountain(
            @RequestParam String localName
    ){
        return ResponseEntity.ok(mountainService.getLocalSelectMountain(localName));
    }
}
