package com.example.clonebuysell.services;

import com.example.clonebuysell.dto.ProductDto;
import com.example.clonebuysell.exception.productException.ProductNotFoundException;
import com.example.clonebuysell.models.Product;
import com.example.clonebuysell.models.User;
import com.example.clonebuysell.repositories.ProductRepository;
import com.example.clonebuysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public List<Product> allProduct() {
        return productRepository.findAll();
    }

    //Возвращаем весь список товаров, если же задан поиск, то возвращает искомые товары
    public List<Product> filteredProductList(String title) {
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
    @Transactional
    public void saveProduct(Principal principal, ProductDto productDto,
                            MultipartFile[] file) throws IOException {

        Product product = new Product();
        product.setUser(getUserByPrincipal(principal));
        extractFromDtoToEntity(productDto, product);

        if (file[0].getSize() > 0) {
            List<String> pathToImage = new ArrayList<>();
            Path path = Paths.get("C:" + File.separator + "photo" + File.separator
                    + principal.getName() + File.separator + product.getTitle() + "-" + UUID.randomUUID());
            saveImageToDisk(file, pathToImage, path);
            product.setImagesPathList(pathToImage);

            if (!pathToImage.isEmpty()) {
                try (FileInputStream fileInputStream = new FileInputStream(pathToImage.get(0))) {
                    product.setPreviewImage(fileInputStream.readAllBytes());
                }
            }
            System.gc(); // без этого вылетает ошибка о том, что томкат не модет удалить temp
        }

        productRepository.save(product);
        log.info("Saving Product: Title: {}; Author email: {}",
                product.getTitle(), product.getUser().getEmail());
    }

    public User getUserByPrincipal(Principal principal) {
      if (principal==null)
           return new User();
       return userRepository.findByEmail(principal.getName());
    }


    //Удаление товара из базы
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    // Находим товар по ID и возвращаем его
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> 
                new ProductNotFoundException("Product not found"));
    }


    //Редактирование товара
    @Transactional
    public void updateProduct(Long id, Principal principal, ProductDto productDto,
                              MultipartFile[] file) throws IOException {
        Product productFromDB = getProductById(id);
        List<String> pathToImage = productFromDB.getImagesPathList();

        if (file[0].getSize() > 0) {
            Path path;
            if (pathToImage.isEmpty()) {
                path = Paths.get("C:" + File.separator + "photo" + File.separator
                        + principal.getName() + File.separator + productDto.getTitle() +
                        "-" + UUID.randomUUID());
            } else {
                path = Path.of(new File(pathToImage.get(0)).getParent());
            }
            saveImageToDisk(file, pathToImage, path);
            System.gc();
        }

        if (productFromDB.getPreviewImage() == null && !pathToImage.isEmpty()) {
            try (FileInputStream fileInputStream = new FileInputStream(pathToImage.get(0))) {
                productFromDB.setPreviewImage(fileInputStream.readAllBytes());
            }
        }

        extractFromDtoToEntity(productDto, productFromDB);

        productRepository.save(productFromDB);
        log.info("Update Product: Title: {}; Author email: {}",
                productFromDB.getTitle(), productFromDB.getUser().getEmail());

    }


    //Удалем все фотографии товара
    @Transactional
    public void deleteImagesOfProduct(Product product) throws IOException {
        if (!product.getImagesPathList().isEmpty()) {
            FileUtils.deleteDirectory(new File(product.getImagesPathList().get(0)).getParentFile());
            product.getImagesPathList().clear();
            product.setPreviewImage(null);
        }
    }

    private void saveImageToDisk(MultipartFile[] file, List<String> pathToImage, Path path) {
        BufferedImage image;
        File imageFile;
        for (MultipartFile f : file) {
            try {
                if (f.getSize() > 0 && (f.getContentType().equals(MediaType.IMAGE_JPEG_VALUE) ||
                        f.getContentType().equals(MediaType.IMAGE_PNG_VALUE))) {
                    image = ImageIO.read(f.getInputStream());
                    imageFile = new File(path + File.separator + f.getOriginalFilename().substring(0,
                            f.getOriginalFilename().lastIndexOf('.')) + ".png");
                    if (!imageFile.getParentFile().exists()) {
                        Files.createDirectories(path);
                    }
                    ImageIO.write(image, "png", imageFile);
                    pathToImage.add(imageFile.getAbsolutePath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Не поддерживаемый файл");
            }
        }
    }

    private static void extractFromDtoToEntity(ProductDto productDto, Product productEntity) {
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setCity(productDto.getCity());
        productEntity.setTitle(productDto.getTitle());
        productEntity.setDateOfCreated(LocalDateTime.now());
    }

}
