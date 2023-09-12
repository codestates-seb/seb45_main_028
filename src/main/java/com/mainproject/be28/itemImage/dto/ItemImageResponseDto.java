package com.mainproject.be28.itemImage.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemImageResponseDto {
    Long itemImageId;
    Long itemId;
    String imageName;
    String URL;
    String representationImage;
}
