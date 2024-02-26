package com.example.clonebuysell.controllers;

import com.example.clonebuysell.models.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@AllArgsConstructor
@Slf4j
public class ImageController {

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @GetMapping("/products/{product}/image/{id}")
    private ResponseEntity<?> imageProduct(@PathVariable Product product,
                                           @PathVariable Long id) throws IOException {
        Path path = Paths.get(product.getImagesPathList().get(Math.toIntExact(id)));
        try {
            byte[] result = Files.readAllBytes(path);
            return ResponseEntity.ok().header(product.getTitle() + id)
                    .contentType(MediaType.IMAGE_PNG)
                    .contentLength(result.length)
                    .body(new InputStreamResource(new ByteArrayInputStream(result)));
        } catch (NoSuchFileException e) {
            logger.info(e.getMessage() + " file could not be uploaded.");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products/{product}/previewImage")
    private ResponseEntity<?> previewImage(@PathVariable Product product) throws IOException {
        byte[] result;
        try {
           result = Files.readAllBytes(Path.of(product.getImagesPathList().get(0)));
            return ResponseEntity.ok().header("previewImage")
                    .contentType(MediaType.IMAGE_PNG)
                    .contentLength(result.length)
                    .body(new InputStreamResource(new ByteArrayInputStream(result)));
        } catch (NoSuchFileException e) {
            logger.info(e.getMessage() + " file could not be uploaded.");
            result = Files.readAllBytes(Path.of("src/main/resources/static/images/noImage.png"));
            return ResponseEntity.badRequest().header("defaultImage")
                    .contentType(MediaType.IMAGE_PNG)
                    .contentLength(result.length)
                    .body(new InputStreamResource(new ByteArrayInputStream(result)));
        }
    }

    @GetMapping("/products/defaultImage")
    private ResponseEntity<?> getDefaultImage() throws IOException {
        Path path = Paths.get("src/main/resources/static/images/noImage.png");
        byte[] result = Files.readAllBytes(path);

        return ResponseEntity.ok().header("defaultImage")
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(result.length)
                .body(new InputStreamResource(new ByteArrayInputStream(result)));
    }
}
