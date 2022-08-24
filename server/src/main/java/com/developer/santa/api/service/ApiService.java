package com.developer.santa.api.service;

import com.developer.santa.api.domain.batchdata.BatchData;
import com.developer.santa.api.domain.batchdata.BatchRepository;
import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.local.LocalRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;


import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;


@Service
@RequiredArgsConstructor
public class ApiService {

    @Value("${mapi.key}")
    String key;

    @Value("${mapi.domain}")
    String domain;

    @Value("${mapi.baseUrl}")
    String baseUrl;

    private final MountainRepository mountainRepository;
    private final LocalRepository localRepository;
    private final CourseRepository courseRepository;
    private final BatchRepository batchRepository;
    private final ApplicationEventPublisher eventPublisher;


    public String connectApi(String geomFilter, String crs, int page, int size, String localName) {
        if (size > 20) return String.valueOf(webClientApi(geomFilter, crs, page, size));

        JSONObject connectApiData = webClientApi(geomFilter, crs, page, size);
        isExistsUrl(geomFilter, crs, page, size, localName, connectApiData);
        return String.valueOf(connectApiData);
    }


    public void dataCrawling(String geomFilter, String crs, int page, int size, String localName){
        JSONObject connectApiData = webClientApi(geomFilter, crs, page, size);
        isExistsUrl(geomFilter, crs, page, size, localName, connectApiData);
    }


    private void isExistsUrl(String geomFilter, String crs, int page, int size, String localName, JSONObject connectApiData) {
        String reqUrl = "/req/data?service=data&request=GetFeature&data=LT_L_FRSTCLIMB"+"&geomFilter="+ geomFilter +"&crs="+ crs +"&size="+ size +"&page="+ page;

        if(batchRepository.existsByReqUrl(reqUrl)) {
            return;
        }
        batchRepository.save(new BatchData(reqUrl));
        eventPublisher.publishEvent(new DataSaveRamda(eventPublisher,
                localName,
                connectApiData.getJSONArray("features"),
                mountainRepository,
                localRepository));
    }


    public JSONObject webClientApi(String geomFilter, String crs, int page, int size){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofMillis(3000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(3000, TimeUnit.MILLISECONDS)));

        Mono<String> mono = WebClient.builder().baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build().get()
                .uri(builder -> builder.path("/req/data")
                        .queryParam("service", "data")
                        .queryParam("request", "GetFeature")
                        .queryParam("data", "LT_L_FRSTCLIMB")
                        .queryParam("key",key)
                        .queryParam("domain", domain)
                        .queryParam("geomFilter", geomFilter)
                        .queryParam("crs", crs)
                        .queryParam("size", size)
                        .queryParam("page", page)
                        .build()
                )
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                });

        return new JSONObject(mono.block())
                .getJSONObject("response")
                .getJSONObject("result")
                .getJSONObject("featureCollection");
    }


    @Getter
    static class DataSaveRamda extends ApplicationEvent {
        private final String localName;
        private final JSONArray parsingval;
        private final MountainRepository mountainRepository;
        private final LocalRepository localRepository;
        public DataSaveRamda(
                Object source,
                String localName,
                JSONArray parsingval,
                MountainRepository mountainRepository,
                LocalRepository localRepository) {
            super(source);
            this.localName=localName;
            this.parsingval=parsingval;
            this.mountainRepository=mountainRepository;
            this.localRepository=localRepository;
        }
    }


    @EventListener()
    public void saveLocalMountainCourse(DataSaveRamda dataSaveRamda){
        IntStream.range(0, dataSaveRamda.getParsingval().length())
                .mapToObj(index -> (JSONObject) dataSaveRamda.getParsingval().get(index))
                .forEach(obj -> {
                    String location = String.valueOf(obj
                            .getJSONObject("geometry").getJSONArray("coordinates")
                            .getJSONArray(0).getJSONArray(0));
                    String mountain = obj.getJSONObject("properties").getString("mntn_nm");
                    String level = obj.getJSONObject("properties").getString("cat_nam");
                    String distance = obj.getJSONObject("properties").getString("sec_len");
                    if(!localRepository.existsByLocalName(dataSaveRamda.getLocalName())){
                        localRepository.save(new Local(dataSaveRamda.getLocalName()));
                    }
                    if(!mountainRepository.existsByMountainName(mountain)) {
                        mountainRepository.save(new Mountain(mountain, localRepository.findByLocalName(dataSaveRamda.getLocalName())));
                    }
                    if(!courseRepository.existsByCourseLocation(location)) {
                        for (char i=65; i<117; i++){
                            if (i == 91) i+=6;
                            if (!courseRepository.existsByCourseName(mountain+" 등산로 "+i)){
                                courseRepository.save(new Course(mountain+" 등산로 "+i,
                                        location,
                                        level,
                                        distance,
                                        mountainRepository.findByMountainName(mountain)));
                                break;
                            }
                        }
                    }
                });
    }
}
