package com.mainproject.be28.itemImage.service;

import com.mainproject.be28.exception.BusinessLogicException;
import com.mainproject.be28.exception.ExceptionCode;
import com.mainproject.be28.itemImage.entity.ItemImage;
import com.mainproject.be28.itemImage.repository.ItemImageRepository;
import com.mainproject.be28.utils.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class ItemImageService {

//    @Value("${itemImgLocation}") 파일 저장 경로 및 리소스 경로 설정 필요
    private String itemImgLocation;
    private final FileService fileService;
    private final ItemImageRepository imgRepository;

    public ItemImageService(FileService fileService, ItemImageRepository imgRepository) {
        this.fileService = fileService;
        this.imgRepository = imgRepository;
    }


    // 상품 이미지 저장
    public void saveItemImg(ItemImage img, MultipartFile itemImgFile) throws IOException {

        String originalName = itemImgFile.getOriginalFilename();
        String imageName = "";
        String url = "";

        // 파일 업로드
        if (originalName != null) {
            imageName = fileService.uploadFile(itemImgLocation, originalName, itemImgFile.getBytes());
            url = "/images/item/" + imageName;
        }

        // 상품 이미지 정보 저장
        img.updateItemImage(originalName, imageName, url);
        imgRepository.save(img);
    }

    // 상품 이미지 수정
    public void updateItemImage(Long itemImgId, MultipartFile itemImgFile) throws IOException {

        // 상품 이미지를 수정했다면
        if (!itemImgFile.isEmpty()) {
            ItemImage savedItemImg = imgRepository.findById(itemImgId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND));

            // 기존 이미지 파일이 존재한다면 삭제
            if (savedItemImg.getImageName() != null) {
                fileService.deleteFile(itemImgLocation + "/" + savedItemImg);
            }

            String originalName = itemImgFile.getOriginalFilename();
            String imageName = fileService.uploadFile(itemImgLocation, originalName, itemImgFile.getBytes());
            String url = "/images/item/" + imageName;
            savedItemImg.updateItemImage(originalName, imageName, url);
        }
    }
}