package com.developer.santa.api.domain.local;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocalDTO {

    private String localName;

    @Builder
    public LocalDTO(String localName){
        this.localName=localName;
    }
}
