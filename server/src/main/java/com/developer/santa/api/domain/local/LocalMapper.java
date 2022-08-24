package com.developer.santa.api.domain.local;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalMapper {
    LocalDTO LocalToLocalDTO(Local local);
}
