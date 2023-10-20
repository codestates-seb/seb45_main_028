package com.mainproject.be28.domain.shopping.item.service;

import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.domain.shopping.item.entity.ItemImage;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
@Transactional
public interface ItemImageService{



   ItemImage uploadImage(MultipartFile file, Item item) throws IOException;

    void deleteImage(Item item);
    List<ItemImage> saveImage(Item item, List<MultipartFile> itemImgFileList) throws IOException;

    List<ItemImage> saveImages(List<MultipartFile> itemImgFileList, Item item) throws IOException;
    void saveImages(List<ItemImage> images);
    void updateImages(List<MultipartFile> itemImgFileList, Item findItem, Item updatedItem) throws IOException;




}