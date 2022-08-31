package com.developer.santa.api.domain.mountain;

import com.developer.santa.api.domain.local.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
    Boolean existsByMountainName(String mountainName);
    Mountain findByMountainName(String mountainName);
    List<Mountain> findByLocal(Local local);
}
