package com.hanghaeclone.dangoon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureResponseDto {
    private String temperature;

    public static TemperatureResponseDto of(String temperature){
        return TemperatureResponseDto.builder()
                .temperature(temperature)
                .build();
    }
}
