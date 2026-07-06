package com.monal.driveEase.DTOs.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {
    private Long vehicleId;
    private Integer rating;
    private String comment;
}
