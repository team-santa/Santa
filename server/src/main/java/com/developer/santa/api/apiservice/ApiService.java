package com.developer.santa.api.apiservice;

import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


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


    public String connectApi(String geomFilter, String crs) {
        Mono<String> mono = WebClient.builder().baseUrl(baseUrl)
                .build().get()
                .uri(builder -> builder.path("/req/data")
                        .queryParam("service", "data")
                        .queryParam("request", "GetFeature")
                        .queryParam("data", "LT_L_FRSTCLIMB")
                        .queryParam("key",key)
                        .queryParam("domain", domain)
                        .queryParam("geomFilter", "BOX(126.876,37.3905,127.0301,37.4982)")
                        .queryParam("crs", "EPSG:4326")
                        .queryParam("size", "100")
                        .queryParam("page", "1")
                        .build()
                )
                .exchangeToMono(response -> {
                    return response.bodyToMono(String.class);
                });

        JSONObject jsonObject = new JSONObject(mono.block());

        JSONArray parsingval = jsonObject
                .getJSONObject("response")
                .getJSONObject("result")
                .getJSONObject("featureCollection")
                .getJSONArray("features");

        IntStream.range(0, parsingval.length())
                .parallel()
                .mapToObj(index -> (JSONObject) parsingval.get(index))
                .forEach(obj -> {
                    Object location = obj
                            .getJSONObject("geometry")
                            .getJSONArray("coordinates")
                            .getJSONArray(0)
                            .getJSONArray(0);
                    //todo save loaction
                    System.out.println(location);
                    String mountain = obj.getJSONObject("properties").getString("mntn_nm");
                    //todo save mountain
                    System.out.println(mountain);
                });

//        //don't remove
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

    return String.valueOf(parsingval);
    }



}
