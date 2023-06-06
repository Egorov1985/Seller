package com.example.buysell.controllers;

import com.example.buysell.models.Image;
import com.example.buysell.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@RestController
@AllArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;


    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) throws IOException {
        Image image = imageRepository.findById(id).orElse(null);


            return ResponseEntity.ok().header("fileName", image.getOriginalFileName())
                    .contentType(MediaType.valueOf(image.getContentType()))
                    .contentLength(image.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));

    }

    @GetMapping("/product/defaultImage")
    private ResponseEntity<?> getDefaultImage() throws IOException {
        Path path = Paths.get("src/main/resources/templates/noImage.png");
        byte [] result = Files.readAllBytes(path);

        return ResponseEntity.ok().header("defaultImage")
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(result.length)
                .body(new InputStreamResource(new ByteArrayInputStream(result)));
    }
}
