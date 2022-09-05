package com.developer.santa.api.service;

import lombok.Getter;
import org.json.JSONArray;


@Getter
class DataCrawlingEvent{
    private Object source;
    private final String localName;
    private final JSONArray parsingval;
    private final String reqUrl;
    public DataCrawlingEvent(
            Object source,
            String localName,
            JSONArray parsingval,
            String reqUrl) {
        this.localName=localName;
        this.parsingval=parsingval;
        this.reqUrl=reqUrl;
    }
}