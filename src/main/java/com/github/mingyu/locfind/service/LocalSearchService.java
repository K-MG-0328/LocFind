package com.github.mingyu.locfind.service;

import com.github.mingyu.locfind.dto.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocalSearchService {

    private final KakaoLocalSearchService kakaoLocalSearchService;
    private final NaverLocalSearchService naverLocalSearchService;
    private final RedisKeywordService redisKeywordService;

    public String removeHtml(String s) {
        Document doc = Jsoup.parse(s);
        String result = doc.text();
        return result;
    }

    public String removeSpace(String s) {
        String result = s.replaceAll("\\s+", "");
        return result;
    }


    public LocalSearchResponse searchKeyword(String query, String size, String x, String y) {

        /* Redis에 키워드 저장 */
        redisKeywordService.increaseKeywordCnt(query);

        /* API 검색 */
        KakaoSearchResponse kakao =  kakaoLocalSearchService.searchKeyword(query, size, x, y);
        NaverSearchResponse naver =  naverLocalSearchService.searchKeyword(query, size);

        List<PlaceDto> kakaoList = new ArrayList<>();
        List<PlaceDto> naverList = new ArrayList<>();
        List<PlaceDto> result = new ArrayList<>();

        kakao.getDocuments().forEach(d -> {
            PlaceDto placeDto = new PlaceDto(d);

            //문자열 공백 제거, 태그 제거
            String name = placeDto.getName();
            name = removeSpace(removeHtml(name));
            placeDto.setName(name);

            kakaoList.add(placeDto);
        });

        naver.getItems().forEach(i -> {
            PlaceDto placeDto = new PlaceDto(i);

            String name = placeDto.getName();
            name = removeSpace(removeHtml(name));
            placeDto.setName(name);

            naverList.add(placeDto);
        });

        Set<String> kakaoNames = kakaoList.stream()
                .map(PlaceDto::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> naverNames = naverList.stream()
                .map(PlaceDto::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> duplicates = new LinkedHashSet<>(kakaoNames);
        duplicates.retainAll(naverNames);

        //중복 데이터 우선 정렬
        for (PlaceDto k : kakaoList) {
            if (duplicates.contains(k.getName())) {
                result.add(k);
            }
        }

        for (PlaceDto k : kakaoList) {
            if (!duplicates.contains(k.getName())) {
                result.add(k);
            }
        }

        for(PlaceDto n : naverList) {
            if (!duplicates.contains(n.getName())) {
                result.add(n);
            }
        }

        /* 검색 키워드 목록 상위 10 */
        List<KeywordDto> topList = redisKeywordService.searchKeywordRank();

        LocalSearchResponse localSearchResponse = new LocalSearchResponse();
        localSearchResponse.setKeywords(topList);
        localSearchResponse.setPlaces(result);

        return localSearchResponse;
    }
}
