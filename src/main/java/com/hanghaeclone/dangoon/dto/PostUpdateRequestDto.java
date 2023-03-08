package com.hanghaeclone.dangoon.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostUpdateRequestDto {
    private String title;
    private String content;
    private int price;
    private String location;
    private List<String> remainingImagesUrlList = new ArrayList<>();
}
