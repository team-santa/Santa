package com.developer.santa.api.service;

import com.developer.santa.api.domain.local.LocalDTO;
import com.developer.santa.api.domain.local.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;

    public LocalDTO getLocal() {
        LocalDTO localDTO = new LocalDTO();
        return localDTO;
    }
}
