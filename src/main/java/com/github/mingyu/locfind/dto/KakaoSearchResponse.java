package com.github.mingyu.locfind.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class KakaoSearchResponse {

    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {
        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("is_end")
        private boolean isEnd;
    }

    @Data
    public static class Document {

        private String id;                      // 장소 ID

        @JsonProperty("place_name")
        private String placeName;              // 장소명, 업체명

        @JsonProperty("category_name")
        private String categoryName;           // 카테고리 이름

        @JsonProperty("category_group_code")
        private String categoryGroupCode;     // 중요 카테고리만 그룹핑한 카테고리 그룹 코드

        @JsonProperty("category_group_name")
        private String categoryGroupName;     // 중요 카테고리만 그룹핑한 카테고리 그룹명
        private String phone;

        @JsonProperty("address_name")
        private String addressName;            // 전체 지번 주소

        @JsonProperty("road_address_name")
        private String roadAddressName;       // 전체 도로명 주소

        private String x;
        private String y;

        @JsonProperty("place_url")
        private String placeUrl;               // 장소 상세페이지 URL
        private String distance;
    }
}
