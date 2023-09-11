package com.mainproject.be28.itemImage.service;

import com.mainproject.be28.item.entity.Item;
import com.mainproject.be28.itemImage.entity.ItemImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@Slf4j
public class ItemImageService {
    private final UploadS3 uploadImageS3;

    public ItemImageService(UploadS3 uploadImageS3) {
        this.uploadImageS3 = uploadImageS3;
    }

    public ItemImage uploadImage(MultipartFile file, Item item) throws IOException {
        StringBuilder fileNameBuilder = new StringBuilder();
        String path = "image/";
        fileNameBuilder.append(path)
                            .append(UUID.randomUUID())
                            .append(file.getOriginalFilename());
        String fileName = fileNameBuilder.toString();
        String savedPath = uploadImageS3.upload(file, fileName);
        log.info("Saved Path : "+savedPath);

        return ItemImage.builder().item(item)
                .originalName(file.getOriginalFilename())
                .imageName(fileName)
                .path(path)
                .build();
    }
}