package com.developer.santa.api.Domain.Load;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Load {
    private String loadName;

    //mountain
    @ManyToOne

    //review
    @OneToMany
}
