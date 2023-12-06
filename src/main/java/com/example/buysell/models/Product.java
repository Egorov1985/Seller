package com.example.buysell.models;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
@NoArgsConstructor
@ToString
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
            "Введите описание товара, не менее 3 символов и не более 1000 символов")
    private String description;

    @Column(name = "price")
    @NotEmpty (message = "Укажите цену")
    private String price;

    @Column(name = "city")
    @NotEmpty(message = "Введите город")
    @Size(min = 3, max = 30, message =
            "Введите город, не менее 3 символов и не более 30 символов")
    private String city;


    @Column(name = "previewImage")
    @Lob
    @ToString.Exclude
    private byte[] previewImage;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn
    @ToString.Exclude
    private User user;

    @Column(name = "dateOfCreated")
    private LocalDateTime dateOfCreated;


    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @ToString.Exclude
    private List<String> imagesPathList = new ArrayList<>();

    //Init Method

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

    //Getter and Setter

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public byte[] getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(byte[] previewImage) {
        this.previewImage = previewImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public List<String> getImagesPathList() {
        return imagesPathList;
    }

    public void setImagesPathList(List<String> imagesPathList) {
        this.imagesPathList = imagesPathList;
    }


    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(title, product.title) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(city, product.city) && Objects.equals(user, product.user) && Objects.equals(dateOfCreated, product.dateOfCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, price, city, user, dateOfCreated);
    }
}
