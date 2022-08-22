package com.developer.santa.api.apibatch;

import org.springframework.batch.item.ItemProcessor;

public class MountainApiProcessor  implements ItemProcessor<Object, Void> {

    @Override
    public Void process(Object item) throws Exception {
        return null;
    }
}
