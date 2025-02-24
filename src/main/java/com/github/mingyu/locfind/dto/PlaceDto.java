package com.github.mingyu.locfind.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private String name;  //장소명
    private String address;
    private String roadAddress;
    private String phone;
    private String category;
    private double x;
    private double y;

    public PlaceDto(KakaoSearchResponse.Document doc) {
        this.name = doc.getPlaceName();
        this.address = doc.getAddressName();
        this.roadAddress = doc.getRoadAddressName();
        this.phone = doc.getPhone();
        this.category = doc.getCategoryName();
        this.x = Double.parseDouble(doc.getX());
        this.y = Double.parseDouble(doc.getY());
    }

    public PlaceDto(NaverSearchResponse.item item) {
        this.name = item.getTitle();
        this.address = item.getAddress();
        this.roadAddress = item.getRoadAddress();
        this.phone = item.getTelephone();
        this.category = item.getCategory();
        this.x = Double.parseDouble(item.getMapx());
        this.y = Double.parseDouble(item.getMapy());
    }
}
