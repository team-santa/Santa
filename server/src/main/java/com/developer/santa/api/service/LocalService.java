package com.developer.santa.api.service;

import com.developer.santa.api.domain.local.LocalDTO;
import com.developer.santa.api.domain.local.LocalMapper;
import com.developer.santa.api.domain.local.LocalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository localRepository;
    private final LocalMapper localMapper;

    public List<LocalDTO> getLocal() {
        return localMapper.LocalListToLocalDTOList(
                localRepository.findAll()
        );
    }
}
