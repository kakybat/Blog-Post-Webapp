package com.kakybat.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class FileService {
    private final Path root;


    public FileService(ResourceLoader resourceLoader){
        this.root = Paths.get("uploads");
    }
    public void init(){
        try {
            Files.createDirectories(root);
        } catch (IOException e){
            throw new RuntimeException("Could not initialize root folder");
        }
    }

    public void save(MultipartFile file){
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(String filename){
        if(filename == null) return null;
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException mue){
            throw new RuntimeException("Error: " + mue.getMessage());
        }
    }

    public Resource userImageLoad(String filename){
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return resource;
            } else {

                // If file not found, return default image from uploads folder
                Path defaultImagePath = root.resolve("user.jpg");
                Resource defaultImageResource = new UrlResource(defaultImagePath.toUri());
                if (defaultImageResource.exists() || defaultImageResource.isReadable()) {
                    return defaultImageResource;
                } else {
                    throw new RuntimeException("Default image not found in 'uploads' folder");
                }
            }
        } catch (MalformedURLException mue){
            throw new RuntimeException("Error: " + mue.getMessage());
        }
    }
}