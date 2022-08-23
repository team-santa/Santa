package com.developer.santa.api.service;

import com.developer.santa.api.domain.batchdata.BatchData;
import com.developer.santa.api.domain.batchdata.BatchRepository;
import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.local.LocalDTO;
import com.developer.santa.api.domain.local.LocalRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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


    public String connectApi(String geomFilter, String crs, int page, int size) {

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

        String reqUrl = "/req/data?service=data&request=GetFeature&data=LT_L_FRSTCLIMB"+"&geomFilter="+geomFilter+"&crs="+crs+"&size="+size+"&page="+page;
        if(!batchRepository.existsByReqUrl(reqUrl)) {
            batchRepository.save(new BatchData(reqUrl));
        }


    return String.valueOf(
            new JSONObject(mono.block())
            .getJSONObject("response")
            .getJSONObject("result")
            .getJSONObject("featureCollection"));
    }

    public void dataCrawling(String geomFilter, String crs, int page, int size, String localName){

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
                            obj.getJSONObject("properties").getString("sec_len"),
                            localName
                    );
                });
        //        for (Object o: parsingval){
//            JSONObject obj = (JSONObject) o;
//            Object location = obj
//                    .getJSONObject("geometry")
//                    .getJSONArray("coordinates")
//                    .getJSONArray(0)
//                    .getJSONArray(0);
//            System.out.println(location);
//            String mountain = obj.getJSONObject("properties").getString("mntn_nm");
//            System.out.println(mountain);
//        }
    }

    public void saveLocationAndMountain(String location, String mountain, String level, String courseDistance, String localName) {

        //local
        if(!localRepository.existsByLocalName(localName)){
            localRepository.save(new Local(localName));
        }

        //mountain
        if(!mountainRepository.existsByMountainName(mountain)) {
            mountainRepository.save(new Mountain(mountain));
        }

        //course
        if(!courseRepository.existsByCourseLocation(location)) {
            for (char i=65; i<117; i++){
                if (i == 91) i+=6;
                if (!courseRepository.existsByCourseName(mountain+" 등산로 "+i)){
                    courseRepository.save(new Course(mountain+" 등산로 "+i, location, level, courseDistance));
                    break;
                }
            }
        }
    }


}
