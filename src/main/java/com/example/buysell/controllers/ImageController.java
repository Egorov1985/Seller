package com.example.buysell.controllers;

import com.example.buysell.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@AllArgsConstructor
public class ImageController {

    @GetMapping("/products/{product}/image/{id}")
    private ResponseEntity<?> imageProduct(@PathVariable Product product,
                                           @PathVariable Long id) throws IOException {
        Path path = Paths.get(product.getImagesPathList().get(Math.toIntExact(id)));

        byte[] result = Files.readAllBytes(path);
        return ResponseEntity.ok().header(product.getTitle() + id)
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(result.length)
                .body(new InputStreamResource(new ByteArrayInputStream(result)));
    }
    @GetMapping("/products/{product}/previewImage")
    private ResponseEntity<?> previewImage (@PathVariable Product product) throws IOException {
        byte[] result = product.getPreviewImage();
        return ResponseEntity.ok().header("previewImage")
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(result.length)
                .body(new InputStreamResource(new ByteArrayInputStream(result)));
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
