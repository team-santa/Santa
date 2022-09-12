package com.developer.santa.api.controller;


import com.developer.santa.api.domain.weather.Weather;
import com.developer.santa.api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/{weatherLocal}")
    public ResponseEntity<Weather> getWeather(@PathVariable String weatherLocal){
        return ResponseEntity.ok(weatherService.getWeather(weatherLocal));
    }

//    @PostMapping("/input")
//    public void postWeather(){
//        weatherService.postWeather();
//    }
}
