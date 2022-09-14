package com.developer.santa.api.domain.weather;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findByWeatherLocal(String weatherLocal);
    Boolean existsByWeatherLocal(String weatherLocal);
}
