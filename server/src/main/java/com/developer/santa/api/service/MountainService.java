package com.developer.santa.api.service;


import com.developer.santa.api.domain.local.LocalRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainDTO;
import com.developer.santa.api.domain.mountain.MountainMapper;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;
    private final LocalRepository localRepository;
    private final MountainMapper mountainMapper;


    public List<MountainDTO> getMountain() {
        return mountainMapper.MountainListToMountainDTOList(mountainRepository.findAll());
    }

    public List<MountainDTO> getLocalSelectMountain(String localName) {
        return mountainMapper.MountainListToMountainDTOList(
                mountainRepository.findByLocal(
                        localRepository.findByLocalName(localName)));
    }

    public Mountain getDetailMountain(String mountain) {
        return mountainRepository.findByMountainName(mountain);
    }
}
