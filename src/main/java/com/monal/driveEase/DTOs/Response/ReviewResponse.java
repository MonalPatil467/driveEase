package com.monal.driveEase.DTOs.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReviewResponse {
    private Long id;
    private String customerName;
    private Integer rating;
    private String comment;
}
