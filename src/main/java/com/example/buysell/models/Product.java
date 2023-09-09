package com.example.buysell.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Введите название товара")
    @Size(min = 3, max = 30, message =
            "Введите название товара, не менее 3 символов и не более 30 символов")
    private String title;

    @Column(name = "description", columnDefinition = "text")
    @NotEmpty(message = "Введите описание товара")
    @Size(min = 3, max = 30, message =
            "Введите описание товара, не менее 3 символов и не более 30 символов")
    private String description;

    @Column(name = "price")
    @Min(value = 0, message = "Цена должна быть больше нуля")
    private Long price;

    @Column(name = "city")
    @NotEmpty(message = "Введите город")
    @Size(min = 3, max = 30, message =
            "Введите город, не менее 3 символов и не более 30 символов")
    private String city;


    @Column(name = "previewImage")
    @Lob
    private byte[] previewImage;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @Column(name = "dateOfCreated")
    private Date dateOfCreated;


    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    private List<String> imagesPathList = new ArrayList<>();


    @PrePersist
    private void init() {
        dateOfCreated = new Date();
    }
}
