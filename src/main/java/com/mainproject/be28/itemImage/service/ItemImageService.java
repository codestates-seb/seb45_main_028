package com.mainproject.be28.itemImage.service;

import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.itemImage.entity.ItemImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@Slf4j
public class ItemImageService {
    private final S3Service S3;

    public ItemImageService(S3Service uploadImageS3) {
        this.S3 = uploadImageS3;
    }

    public ItemImage uploadImage(MultipartFile file, Item item) throws IOException {
        StringBuilder fileNameBuilder = new StringBuilder();
        String path = "image/";
        fileNameBuilder.append(path)
                            .append(UUID.randomUUID())
                            .append(file.getOriginalFilename());
        String fileName = fileNameBuilder.toString();
        String savedPath = S3.upload(file, fileName);
        log.info("Saved Path : "+savedPath);

        return ItemImage.builder().item(item)
                .originalName(file.getOriginalFilename())
                .imageName(fileName)
                .path(path)
                .build();
    }

    public void deleteImage(Item item) {
        List<ItemImage> images = item.getImages();
        for (ItemImage image : images) {
            S3.deleteFile(image.getImageName());
        }
    }
}