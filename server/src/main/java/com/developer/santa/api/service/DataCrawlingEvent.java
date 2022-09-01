package com.developer.santa.api.service;

import lombok.Getter;
import org.json.JSONArray;


@Getter
class DataCrawlingEvent{
    private Object source;
    private final String localName;
    private final JSONArray parsingval;
    public DataCrawlingEvent(
            Object source,
            String localName,
            JSONArray parsingval) {
        this.localName=localName;
        this.parsingval=parsingval;
    }
}