package com.example.buysell.services;

import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.repositories.ProductRepository;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    //Возвращаем весь список товаров, если задан ппоиск, то возвращает искомые товары
    public List<Product> listProducts(String title) {
        List<Product> productList = productRepository.findAll();
        if (title != null) {
            List<Product> listSearch = new ArrayList<>();
            for (Product product : productList) {
                if (product.getTitle().startsWith(title))
                    listSearch.add(product);
            }
            return listSearch;
        }
        return productList;
    }

    // Сохранение товара в Базу Данных
    public void saveProduct(Principal principal, Product product,
                            MultipartFile[] file) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        BufferedImage image = null;
        List<String> pathToImage = new ArrayList<>();

        Path path = Paths.get("C:" + File.separator + "photo" + File.separator
                + principal.getName() + File.separator + product.getTitle()+ UUID.randomUUID());
        File imageFile = null;
        for (MultipartFile f : file) {
            try {
                if (f.getSize() > 0) {
                    image = ImageIO.read(f.getInputStream());
                    imageFile = new File(path + File.separator + f.getOriginalFilename().substring(0,
                            f.getOriginalFilename().lastIndexOf('.')) + ".png");
                    if (!imageFile.getParentFile().exists()){
                        Files.createDirectories(path);
                    }
                    ImageIO.write(image, "png", imageFile);
                    pathToImage.add(imageFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        product.setImagesPathList(pathToImage);
        if (!pathToImage.isEmpty()){
            try(FileInputStream fileInputStream = new FileInputStream(pathToImage.get(0))) {
                product.setPreviewImage(fileInputStream.readAllBytes());
            }
        }
        System.gc();// без этого вылетает ошибка о том, что томкат не модет удалить temp
        log.info("Saving Product: Title: {}; Author email: {}",
                product.getTitle(), product.getUser().getEmail());
        product.setDateOfCreated(new Date());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null)
            return new User();
        return userRepository.findByEmail(principal.getName());
    }

    //Удаление товара из базы
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    // Находим товар по ID и возвращаем его
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }


    //Редактирование товара
    public void updateProduct(Long id, Principal principal, Product product,
                              MultipartFile[] file) throws IOException {
        Product productFromDB = getProductById(id);
        product.setUser(getUserByPrincipal(principal));
        List<String> pathToImage = productFromDB.getImagesPathList();
        product.setImagesPathList(pathToImage);
        Path path = Paths.get("C:" + File.separator + "photo" + File.separator
                + principal.getName() + File.separator + product.getTitle()+ UUID.randomUUID());
        File imageFile = null;
        BufferedImage image = null;
        for (MultipartFile f : file) {
            try {
                if (f.getSize() > 0) {
                    image = ImageIO.read(f.getInputStream());
                    imageFile = new File(path + File.separator + f.getOriginalFilename().substring(0,
                            f.getOriginalFilename().lastIndexOf('.')) + ".png");
                    if (!imageFile.getParentFile().exists()){
                        Files.createDirectories(path);
                    }
                    ImageIO.write(image, "png", imageFile);
                    pathToImage.add(imageFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.gc();
        if (product.getPreviewImage()==null && !pathToImage.isEmpty()){
           try (FileInputStream fileInputStream = new FileInputStream(pathToImage.get(0))) {
               product.setPreviewImage(fileInputStream.readAllBytes());
           }
        }
        log.info("Update Product: Title: {}; Author email: {}",
                product.getTitle(), product.getUser().getEmail());
        product.setDateOfCreated(new Date());
        productRepository.save(product);
    }


    //Удалем все фотографии товара
    public void deleteImagesOfProduct(Product product) {
        product.getImagesPathList().clear();
        product.setPreviewImage(null);
    }

}
