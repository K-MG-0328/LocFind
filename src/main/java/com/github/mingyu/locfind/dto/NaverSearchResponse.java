package com.github.mingyu.locfind.dto;

import lombok.Data;

import java.util.List;

@Data
public class NaverSearchResponse {
    private String lastBuildDate;
    private String total;
    private String start;
    private String display;
    private List<item> items;


    @Data
    public static class item {
        private String title;               //  업체, 기관의 이름
        private String link;                //	업체, 기관의 상세 정보 URL
        private String category;            //  업체, 기관의 분류 정보
        private String description;         //  업체, 기관에 대한 설명
        private String telephone;
        private String address;             //  업체, 기관명의 지번 주소
        private String roadAddress;         //  업체, 기관명의 도로명 주소
        private String mapx;
        private String mapy;
    }
}
