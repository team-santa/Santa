package com.developer.santa.api.service;


import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;


    public List<Mountain> getMountain() {
        return mountainRepository.findAll();
    }
}
