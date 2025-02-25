package com.github.mingyu.locfind.dto;

import lombok.Data;

import java.util.List;

@Data
public class LocalSearchResponse {

    List<KeywordDto> keywords;  // 상위 키워드
    List<PlaceDto> places;      // 장소 검색
}
