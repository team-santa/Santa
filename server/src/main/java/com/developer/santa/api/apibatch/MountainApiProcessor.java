package com.developer.santa.api.apibatch;

import com.developer.santa.api.domain.batchdata.BatchData;
import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

public class MountainApiProcessor  implements ItemProcessor<BatchData, List<Mountain>> {


    private final MountainRepository mountainRepository;
    private final CourseRepository courseRepository;

    @Value("${mapi.key}")
    String key;

    @Value("${mapi.domain}")
    String domain;

    @Value("${mapi.baseUrl}")
    String baseUrl;

    public MountainApiProcessor(MountainRepository mountainRepository, CourseRepository courseRepository) {
        this.mountainRepository = mountainRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Mountain> process(BatchData item){
        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs()
                            .jackson2JsonEncoder
                                    (new Jackson2JsonEncoder(new ObjectMapper(),
                                            MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer
                            .defaultCodecs()
                            .jackson2JsonDecoder
                                    (new Jackson2JsonDecoder(
                                            new ObjectMapper(),
                                            MediaType.APPLICATION_JSON));
                }).build();

        String RequestUri = baseUrl+item.getReqUrl()+"&domain="+domain+"&key="+key;

        Mono<String> mono = WebClient.builder()
                .exchangeStrategies(strategies).build()
                .get()
                .uri(RequestUri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(String.class)
                .log();

        JSONArray parsingval = new JSONObject(mono.block())
                .getJSONObject("response")
                .getJSONObject("result")
                .getJSONObject("featureCollection")
                .getJSONArray("features");

        IntStream.range(0, parsingval.length())
                .mapToObj(index -> (JSONObject) parsingval.get(index))
                .forEach(obj -> {
                    saveLocationAndMountain(
                            String.valueOf(obj
                                    .getJSONObject("geometry").getJSONArray("coordinates")
                                    .getJSONArray(0).getJSONArray(0)),
                            obj.getJSONObject("properties").getString("mntn_nm"),
                            obj.getJSONObject("properties").getString("cat_nam"),
                            obj.getJSONObject("properties").getString("sec_len")
                    );
                });
        return null;
    }
    public void saveLocationAndMountain(String location, String mountain, String level, String courseDistance) {
        if(!courseRepository.existsByCourseLocation(location)) {
            courseRepository.save(new Course(mountain+" 등산로", location, level, courseDistance));
        }
        if(!mountainRepository.existsByMountainName(mountain)) {
            mountainRepository.save(new Mountain(mountain));
        }
    }
}
