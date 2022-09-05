package com.developer.santa.api.service;


import com.developer.santa.api.domain.weather.Weather;
import com.developer.santa.api.domain.weather.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public Weather getWeather(String weatherLocal) {
        if (weatherRepository.existsByWeatherLocal(weatherLocal)){
            return weatherRepository.findByWeatherLocal(weatherLocal);
        }
        throw new RuntimeException();
    }

    public void postWeather() {
        weatherRepository.save(new Weather(1L,"서울특별시","서울","11B10101"));
        weatherRepository.save(new Weather(2L,"인천광역시","인천","11B20201"));
        weatherRepository.save(new Weather(3L,"경기도","수원","11B20601"));
        weatherRepository.save(new Weather(4L,"부산광역시","부산","11H20201"));
    }
}
