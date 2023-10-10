package com.mainproject.be28.domain.shopping.item.service;

import com.mainproject.be28.domain.shopping.item.repository.ItemImageRepository;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.entity.ItemImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@Slf4j
public class ItemImageService {
    private final S3Service S3;
    private final ItemImageRepository itemImageRepository;

    public ItemImageService(S3Service s3, ItemImageRepository itemImageRepository) {
        S3 = s3;
        this.itemImageRepository = itemImageRepository;
    }


    public ItemImage uploadImage(MultipartFile file, Item item) throws IOException {
        StringBuilder fileNameBuilder = new StringBuilder();
        String path = "image/";
        fileNameBuilder
                            .append(path)
                            .append(item.getCategory().toUpperCase())
                            .append("_")
                            .append(UUID.randomUUID().toString(), 0, 10)
                            .append("_")
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

    public List<ItemImage> saveImage(Item item, List<MultipartFile> itemImgFileList) throws IOException {
        List<ItemImage> images = new ArrayList<>();
        for (int i = 0; i < itemImgFileList.size(); i++) {
            MultipartFile file = itemImgFileList.get(i);
            ItemImage image = uploadImage(file, item);
            if (i == 0) {
                image.setRepresentationImage("YES");
            } else {
                image.setRepresentationImage("NO");
            }
            images.add(image);
        }
        return images;
    }

    public List<ItemImage> saveImages(List<MultipartFile> itemImgFileList, Item item) throws IOException {
        List<ItemImage> images = new ArrayList<>();
        if (itemImgFileList != null){
            images = saveImage(item, itemImgFileList);
        }
        return images;
    }
    public void saveImages(List<ItemImage> images) {
        if(images.size()>0){
            itemImageRepository.saveAll(images);}
    }
    public void updateImages(List<MultipartFile> itemImgFileList, Item findItem, Item updatedItem) throws IOException {
        List<ItemImage> images = findItem.getImages()==null?new ArrayList<>():findItem.getImages();

        //새로운 파일 이미지가 있다면, 새로 추가
        if (itemImgFileList != null) {
            for (MultipartFile image : itemImgFileList) {
                ItemImage img = uploadImage(image, updatedItem);
                images.add(img);
            }
            //대표 이미지 설정이 안되어있다면, 맨 첫번째 이미지 대표이미지로 설정
            setRepresentationImage(updatedItem, images);
        }
    }

    private void setRepresentationImage(Item updatedItem, List<ItemImage> images) {
        ItemImage repImg = images.get(0);
        if(repImg.getRepresentationImage()==null||!repImg.getRepresentationImage().equals("YES")) {
            repImg.setRepresentationImage("YES");
            images.set(0, repImg);
        }
        updatedItem.setImages(images);
        itemImageRepository.saveAll(images);
    }


}