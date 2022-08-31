package com.developer.santa.api.service;

import com.developer.santa.api.domain.local.LocalRepository;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.Getter;
import org.json.JSONArray;
import org.springframework.context.ApplicationEvent;

@Getter
class DataCrawlingEvent{
    private Object source;
    private final String localName;
    private final JSONArray parsingval;
    private final MountainRepository mountainRepository;
    private final LocalRepository localRepository;
    public DataCrawlingEvent(
            Object source,
            String localName,
            JSONArray parsingval,
            MountainRepository mountainRepository,
            LocalRepository localRepository) {
        this.localName=localName;
        this.parsingval=parsingval;
        this.mountainRepository=mountainRepository;
        this.localRepository=localRepository;
    }
}