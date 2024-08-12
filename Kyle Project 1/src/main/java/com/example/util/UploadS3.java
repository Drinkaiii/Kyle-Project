package com.example.util;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UploadS3 implements FileSave  {

    @Value("${aws.s3.bucketName}")
    String bucketName;
    @Value("${aws.region}")
    String region;
    @Value("${tempImagePath}")
    private String tempImagePath;

    public String oneFile(MultipartFile file) {

        try {

            //set file name
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));

            // save tempFile to local
            Path tempPath = Paths.get(tempImagePath).resolve(fileName);
            Files.copy(file.getInputStream(), tempPath);

            //upload to S3
            final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.fromName(region))
                    .withCredentials(new InstanceProfileCredentialsProvider(false))
                    .build();
            s3Client.putObject(bucketName, fileName, tempPath.toFile());
            String fileUrl = s3Client.getUrl(bucketName, fileName).toString();
            Files.delete(tempPath);

            return fileUrl;

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public ArrayList<String> multiFile(List<MultipartFile> files) {
        ArrayList<String> fileUrls = new ArrayList<>();
        try {
            for (MultipartFile file : files) {

                //set file name
                String fileName = file.getOriginalFilename();
                fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));

                // save tempFile to local
                Path tempPath = Paths.get(tempImagePath).resolve(fileName);
                Files.copy(file.getInputStream(), tempPath);

                //upload to S3
                final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                        .withRegion(Regions.fromName(region))
                        .withCredentials(new InstanceProfileCredentialsProvider(false))
                        .build();
                s3Client.putObject(bucketName, fileName, tempPath.toFile());
                fileUrls.add(s3Client.getUrl(bucketName, fileName).toString());

                // delete temp file
                Files.delete(tempPath);

            }
            return fileUrls;
        } catch (IOException e) {
            e.printStackTrace();
            return fileUrls;
        }
    }

}
