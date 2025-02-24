package com.github.mingyu.locfind.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.github.mingyu.locfind.dto.KakaoSearchResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KakaoLocalSearchService {

    @Value("${kakao.rest-api-key}")
    private String kakaoRestApiKey;

    private static final String KAKAO_LOCAL_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";

    /**
     * 로컬 키워드 검색을 호출하는 메서드
     */
    public KakaoSearchResponse searchKeyword(String query, String size, String x, String y) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);

        String url = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_URL)
                .queryParam("query", query)
                .queryParam("size", size)
                .queryParam("x", x)
                .queryParam("y", y)
                .build()
                .toUriString();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoSearchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KakaoSearchResponse.class
        );

        return response.getBody();
    }
}
