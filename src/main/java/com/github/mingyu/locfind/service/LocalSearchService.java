package com.github.mingyu.locfind.service;

import com.github.mingyu.locfind.dto.KakaoSearchResponse;
import com.github.mingyu.locfind.dto.NaverSearchResponse;
import com.github.mingyu.locfind.dto.PlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;

@Service
@RequiredArgsConstructor
public class LocalSearchService {

    private final KakaoLocalSearchService kakaoLocalSearchService;
    private final NaverLocalSearchService naverLocalSearchService;

    public List<PlaceDto> searchKeyword(String query, String size, String x, String y) {
        KakaoSearchResponse kakao =  kakaoLocalSearchService.searchKeyword(query, size, x, y);
        NaverSearchResponse naver =  naverLocalSearchService.searchKeyword(query, size);

        List<PlaceDto> kakaoList = new ArrayList<>();
        List<PlaceDto> naverList = new ArrayList<>();
        List<PlaceDto> result = new ArrayList<>();

        kakao.getDocuments().forEach(d -> {
            PlaceDto placeDto = new PlaceDto(d);
            kakaoList.add(placeDto);
        });

        naver.getItems().forEach(i -> {
            PlaceDto placeDto = new PlaceDto(i);
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

        return result;
    }
}
