package com.developer.santa.api.domain.batchdata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<BatchData, Long> {
    Boolean existsByReqUrl(String reqUrl);
}
