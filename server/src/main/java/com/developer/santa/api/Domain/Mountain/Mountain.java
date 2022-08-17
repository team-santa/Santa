package com.developer.santa.api.Domain.Mountain;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Mountain {
    private String mountainName;

    //local
    @ManyToOne

    //load
    @OneToMany
}
