package com.github.mingyu.locfind.service;

import com.github.mingyu.locfind.dto.KakaoSearchResponse;
import com.github.mingyu.locfind.dto.NaverSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NaverLocalSearchService {


    @Value("${naver.client-id}")
    private String naverClientId;

    @Value("${naver.client-secret}")
    private String naverClientSecret;

    private static final String NAVER_LOCAL_SEARCH_URL = "https://openapi.naver.com/v1/search/local.json";

    /**
     *  로컬 키워드 검색을 호출하는 메서드
     */
    public NaverSearchResponse searchKeyword(String query, String display) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);

        String url = UriComponentsBuilder.fromHttpUrl(NAVER_LOCAL_SEARCH_URL)
                .queryParam("query", query)
                .queryParam("display", display)
                .build()
                .toUriString();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<NaverSearchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                NaverSearchResponse.class
        );

        return response.getBody();
    }
}
