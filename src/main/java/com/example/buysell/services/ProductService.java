package com.example.buysell.services;

import com.example.buysell.models.Image;
import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.repositories.ImageRepository;
import com.example.buysell.repositories.ProductRepository;
import com.example.buysell.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;


    //Возвращаем весь список товаров, если задан ппоиск, то возвращает искомые товары
    public List<Product> listProducts(String title) {
        List<Product> productList = productRepository.findAll();
        if (title!=null){
            List<Product> listSearch = new ArrayList<>();
            for (Product product: productList){
                if (product.getTitle().startsWith(title))
                    listSearch.add(product);
            }
            return listSearch;
        }
       return productList;
    }

    // Сохранение товара в Базу Данных
    public void saveProduct(Principal principal, Product product, MultipartFile[] file) throws IOException {
        product.setUser(getUserByPrincipal(principal));
        for (MultipartFile f: file){
            if (f.getSize()!=0) {
                try {
                    Image image = toImageEntity(f);
                    product.addImageToProduct(image);
                    if (product.getImages().size() == 1) {
                        product.getImages().get(0).setPreviewImage(true);
                        productRepository.save(product);  // сохраняем в БД, чтобы можно было присвоить ID привьюшней фотографии
                        imageRepository.save(image);  // сохраняем в БД, чтобы можно было присвоить ID привьюшней фотографии
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка загрузки фотографии " + new IOException(e));
                }
            }
        }
        // Устанавливаем флажок на привьюшную фотографию
        if (!product.getImages().isEmpty()){
            product.setPreviewImageId(product.getImages().get(0).getId());
        }

        log.info("Saving Product: Title: {}; Author email: {}",
                product.getTitle(), product.getUser().getEmail());
        product.setDateOfCreated(new Date());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal==null)
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
    public void updateProduct(Product product, Long id, MultipartFile[] file, Principal principal) {
        product.setUser(getUserByPrincipal(principal));
        for (MultipartFile f: file){
            if (f.getSize()!=0) {
                try {
                    Image image = toImageEntity(f);
                    product.addImageToProduct(image);
                    if (product.getImages().size() == 1 && getProductById(id).getImages().isEmpty()) {
                        product.getImages().get(0).setPreviewImage(true);
                        imageRepository.save(image);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        log.info("Update Product: Title: {}; Author email: {}",
                product.getTitle(), product.getUser().getEmail());
        if (!product.getImages().isEmpty()){
            product.setPreviewImageId(getProductById(id).getImages().get(0).getId());
        }
        product.setDateOfCreated(new Date());
        productRepository.save(product);
    }


     //Удалем все фотографии товара
    public void deleteImagesOfProduct(Long id){
        Product product = getProductById(id);
        List <Long> idImageDeleteOfProduct = new ArrayList<>();
        if (!product.getImages().isEmpty()){
            product.getImages().forEach(image -> idImageDeleteOfProduct.add(image.getId()));
        }
        product.setImages(null); // очищаем лист, чтобы не произошло объединение (в jpa вызывается метод "merge") при сохранении
        product.setPreviewImageId(null);
        for (Long idImage: idImageDeleteOfProduct){
            imageRepository.deleteById(idImage);
        }
    }

    //Преобразовыем файлы в изображения
    private Image toImageEntity (MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
}
