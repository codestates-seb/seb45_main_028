package com.mainproject.be28.domain.shopping.item.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.mainproject.be28.global.exception.BusinessLogicException;
import com.mainproject.be28.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    public String upload(MultipartFile multipartFile, String fileName) throws IOException {
        try {
            ObjectMetadata metadata = convertMetaData(multipartFile);
            putS3(multipartFile, fileName, metadata);
        } catch (Exception e) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_NOT_FOUND);
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public String deleteFile(String keyName) {

        String result = "success";

        try {
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, keyName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucket, keyName);
            } else {
                result = "file not found";
            }
        } catch (Exception e) {
            log.debug("Delete File failed", e);
        }

        return result;
    }
    private static ObjectMetadata convertMetaData(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());
        return metadata;
    }

    private void putS3(MultipartFile multipartFile, String fileName, ObjectMetadata metadata) throws IOException {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3Client.putObject(putObjectRequest);
    }

}