package com.example.buysell.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column (name = "title")
    @NotEmpty(message = "Введите название товара")
    @Size (min = 3, max = 30, message =
            "Введите название товара, не менее 3 символов и не более 30 символов")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    @NotEmpty(message = "Введите описание товара")
    @Size (min = 3, max = 30, message =
            "Введите описание товара, не менее 3 символов и не более 30 символов")
    private String description;

    @Column(name = "price")
    @Min(value = 0, message = "Цена должна быть больше нуля")
    private Long price;

    @Column(name = "city")
    @NotEmpty(message = "Введите город")
    @Size (min = 3, max = 30, message =
            "Введите город, не менее 3 символов и не более 30 символов")
    private String city;

    @Column(name = "author")
    @NotEmpty(message = "Введите свое имя")
    @Size (min = 3, max = 30, message =
            "Введите свое имя, не менее 3 символов и не более 30 символов")
    private String author;

    @Column (name = "previewImageId")
    private Long previewImageId;

    @Column (name = "dateOfCreated")
    private LocalDateTime dateOfCreated;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
        if (!images.isEmpty()){
            images.get(0).setPreviewImage(true);
        }
    }

    public void addImageToProduct(Image image){
        image.setProduct(this);
        images.add(image);
    }
}
