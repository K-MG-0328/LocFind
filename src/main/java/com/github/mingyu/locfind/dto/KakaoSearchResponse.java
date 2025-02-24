package com.github.mingyu.locfind.dto;

import lombok.Data;
import java.util.List;

@Data
public class KakaoSearchResponse {

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {
        private int totalCount;
        private int pageableCount;
        private boolean isEnd;
    }

    @Data
    public static class Document {
        private String id;                      // 장소 ID
        private String placeName;              // 장소명, 업체명
        private String categoryName;           // 카테고리 이름
        private String categoryGroupCode;     // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
        private String categoryGroupName;     // 중요 카테고리만 그룹핑한 카테고리 그룹명
        private String phone;
        private String addressName;            // 전체 지번 주소
        private String roadAddressName;       // 전체 도로명 주소
        private String x;
        private String y;
        private String placeUrl;               // 장소 상세페이지 URL
        private String distance;
    }
}
