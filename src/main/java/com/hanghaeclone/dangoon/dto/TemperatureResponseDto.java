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
    private Double temperature;

    public static TemperatureResponseDto of(Double temperature){
        return TemperatureResponseDto.builder()
                .temperature(temperature)
                .build();
    }
}
