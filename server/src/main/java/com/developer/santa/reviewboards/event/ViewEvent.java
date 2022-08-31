package com.developer.santa.reviewboards.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter

public class ViewEvent extends ApplicationEvent {
    private final Long reviewBoardId;
    private final String clientIp;

    public ViewEvent(Object source,  Long reviewBoardId, String clientIp) {
        super(source);
        this.clientIp = clientIp;
        this.reviewBoardId = reviewBoardId;
    }
}
