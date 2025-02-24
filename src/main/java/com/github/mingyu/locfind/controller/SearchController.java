package com.github.mingyu.locfind.controller;

import com.github.mingyu.locfind.dto.KakaoSearchResponse;
import com.github.mingyu.locfind.dto.NaverSearchResponse;
import com.github.mingyu.locfind.dto.PlaceDto;
import com.github.mingyu.locfind.service.KakaoLocalSearchService;
import com.github.mingyu.locfind.service.LocalSearchService;
import com.github.mingyu.locfind.service.NaverLocalSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SearchController {

    private final LocalSearchService localSearchService;
    private final KakaoLocalSearchService kakaoLocalSearchService;
    private final NaverLocalSearchService naverLocalSearchService;


    @GetMapping("/search")
    public ResponseEntity<List<PlaceDto>> searchKeyword(@RequestParam String query, @RequestParam(defaultValue = "5") int size) {
        List<PlaceDto> response = localSearchService.searchKeyword(query, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/kakaoSearch")
    public ResponseEntity<KakaoSearchResponse> searchKaKaoKeyword(@RequestParam String query,
                                                             @RequestParam(defaultValue = "5") int size) {
        KakaoSearchResponse response = kakaoLocalSearchService.searchKeyword(query, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/naverSearch")
    public ResponseEntity<NaverSearchResponse> searchNaverKeyword(@RequestParam String query,
                                                             @RequestParam(defaultValue = "5") int display) {
        NaverSearchResponse response = naverLocalSearchService.searchKeyword(query, display);
        return ResponseEntity.ok(response);
    }
}
