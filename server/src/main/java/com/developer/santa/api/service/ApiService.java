package com.developer.santa.api.service;

import com.developer.santa.api.domain.batchdata.BatchData;
import com.developer.santa.api.domain.batchdata.BatchRepository;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;



@Service
@RequiredArgsConstructor
public class ApiService {

    @Value("${mapi.key}")
    String key;

    @Value("${mapi.domain}")
    String domain;

    @Value("${mapi.baseUrl}")
    String baseUrl;


    private final BatchRepository batchRepository;
    private final ApplicationEventPublisher eventPublisher;


    public Mono<String> connectApi(String geomFilter, String crs, int page, int size, String localName) {

        if (size > 20) return webClientApi(geomFilter, crs, page, size);
        Mono<String> mono = webClientApi(geomFilter, crs, page, size);
        isExistsUrl(geomFilter, crs, page, size, localName, mono);
        return mono;
    }


    public void dataCrawling(String geomFilter, String crs, int page, int size, String localName){

        isExistsUrl(geomFilter, crs, page, size, localName, webClientApi(geomFilter, crs, page, size));
    }


    private void isExistsUrl(String geomFilter, String crs, int page, int size, String localName, Mono<String> mono) {
        String reqUrl = "/req/data?service=data&request=GetFeature&data=LT_L_FRSTCLIMB"+"&geomFilter="+ geomFilter +"&crs="+ crs +"&size="+ size +"&page="+ page;

        if(batchRepository.existsByReqUrl(reqUrl)) {
            new JSONObject();
            return;
        }
        JSONArray connectApiData = new JSONObject(mono.block())
                .getJSONObject("response")
                .getJSONObject("result")
                .getJSONObject("featureCollection")
                .getJSONArray("features");

        eventPublisher.publishEvent(new DataCrawlingEvent(eventPublisher, localName, connectApiData, reqUrl));
    }


    public Mono<String> webClientApi(String geomFilter, String crs, int page, int size){
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .responseTimeout(Duration.ofMillis(3000))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(3000, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(3000, TimeUnit.MILLISECONDS)));

        return WebClient.builder().baseUrl(baseUrl)
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
    }
}