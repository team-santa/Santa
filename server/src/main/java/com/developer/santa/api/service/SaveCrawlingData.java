package com.developer.santa.api.service;

import com.developer.santa.api.domain.batchdata.BatchData;
import com.developer.santa.api.domain.batchdata.BatchRepository;
import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.local.LocalRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.*;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SaveCrawlingData {
    private final MountainRepository mountainRepository;
    private final LocalRepository localRepository;
    private final CourseRepository courseRepository;
    private final BatchRepository batchRepository;

    @EventListener()
    @Transactional(timeout = 3)
    public void saveLocalMountainCourse(DataCrawlingEvent dataSaveRamda) throws InterruptedException {
        ExecutorService executor = new ThreadPoolExecutor(1, 1, 3L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.execute(()->{
            if (!localRepository.existsByLocalName(dataSaveRamda.getLocalName())) {
                localRepository.save(new Local(dataSaveRamda.getLocalName()));
            }
            IntStream.range(0, dataSaveRamda.getParsingval().length())
                    .mapToObj(index -> (JSONObject) dataSaveRamda.getParsingval().get(index))
                    .forEach(obj -> {
                                String location = String.valueOf(obj
                                        .getJSONObject("geometry").getJSONArray("coordinates")
                                        .getJSONArray(0).getJSONArray(0));
                                String mountain = obj.getJSONObject("properties").getString("mntn_nm");
                                String level = obj.getJSONObject("properties").getString("cat_nam");
                                String distance = obj.getJSONObject("properties").getString("sec_len");
                                if (!mountainRepository.existsByMountainName(mountain)) {
                                    mountainRepository.save(new Mountain(mountain, localRepository.findByLocalName(dataSaveRamda.getLocalName())));
                                }
                                if (!courseRepository.existsByCourseLocation(location)) {
                                    for (char i = 65; i < 117; i++) {
                                        if (i == 91) i += 6;
                                        if (!courseRepository.existsByCourseName(mountain + " ????????? " + i)) {
                                            courseRepository.save(new Course(mountain + " ????????? " + i,
                                                    location,
                                                    level,
                                                    distance,
                                                    mountainRepository.findByMountainName(mountain)));
                                            break;
                                        }
                                    }
                                }
                    });
            batchRepository.save(new BatchData(dataSaveRamda.getReqUrl()));
        });
        executor.shutdown();
    }
}
