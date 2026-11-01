package com.monal.driveEase.DTOs.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewSummaryResponse {


    private boolean available;

    private int reviewCount;


    private Double averageRating;

    private String summary;
    private List<String> pros;
    private List<String> cons;


    private String message;
}