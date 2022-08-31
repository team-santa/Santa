package com.developer.santa.member.common;

import com.developer.santa.member.oauth.entity.ProviderType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CommonDocumentController {

    @GetMapping("/docs")
    public ResponseEntity<EnumDocs> providerId() {
        Map<String, String> provider = Arrays.stream(ProviderType.values()).collect(Collectors.toMap(ProviderType::getProviderId, ProviderType::getDecription));

        return new ResponseEntity<>(new EnumDocs(provider), HttpStatus.OK);
    }
}
