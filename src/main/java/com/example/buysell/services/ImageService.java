package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> imagePreviewOfProductList(){

       List<Image> images = new ArrayList<>();
       imageRepository.findAll().forEach(image -> {
                  if (image.isPreviewImage()==true){
                      images.add(image);
                  }
               });
        System.out.println(images);
       return images;
    }


}
