package com.developer.santa.api;


import com.developer.santa.api.domain.batchdata.BatchData;
import com.developer.santa.api.domain.batchdata.BatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
public class TestBatchRepository {
    @Autowired
    BatchRepository batchRepository;

    @Test
    @Commit
    public void batchData01(){
        for(int i=1; i<101; i++){
            batchRepository.save(new BatchData("urlData"+i));
        }
    }
}
