package com.developer.santa.api.domain.mountain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
    Boolean existsByMountainName(String mountainName);
}
