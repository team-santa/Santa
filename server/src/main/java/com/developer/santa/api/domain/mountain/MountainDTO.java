package com.developer.santa.api.domain.mountain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MountainDTO {

    private String mountainName;

    @Builder
    public MountainDTO(String mountainName){
        this.mountainName=mountainName;
    }
}
