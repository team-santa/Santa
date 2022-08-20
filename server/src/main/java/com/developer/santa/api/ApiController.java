package com.developer.santa.api;

import com.developer.santa.api.apiservice.ApiService;
import com.developer.santa.api.domain.load.Load;
import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.mountain.Mountain;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;


    @GetMapping("/connect")
    public String connect(@RequestParam String geomFilter, String crs) {
        return apiService.connectApi(geomFilter, crs);
    }

    @GetMapping("/local")
    public ResponseEntity<Local> getLocal(){
        Local local = new Local();
        return ResponseEntity.ok(local);
    }

    @GetMapping("/mountain")
    public ResponseEntity<Mountain> getMountain(){
        Mountain mountain = new Mountain();
        return ResponseEntity.ok(mountain);
    }

    @GetMapping("/load")
    public ResponseEntity<Load> getLoad(){
        Load load = new Load();
        return ResponseEntity.ok(load);
    }

}
