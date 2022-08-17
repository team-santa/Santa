package com.developer.santa.api;


import com.developer.santa.api.Domain.Load.Load;
import com.developer.santa.api.Domain.Local.Local;
import com.developer.santa.api.Domain.Mountain.Mountain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {



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
