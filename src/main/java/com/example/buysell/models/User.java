package com.example.buysell.models;

import com.example.buysell.Annotation.Phone;
import com.example.buysell.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@ToString
public class User implements UserDetails, Serializable {

    public User(String email, String phoneNumber, String name, boolean active, String password, Set<Role> roles) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.active = active;
        this.password = password;
        this.roles = roles;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Email
    @Column (name = "email", unique = true)
    @NotEmpty (message = "Введите корректный email")
    private String email;

    @Column (name = "phone_Number")
    @Phone(message = "Введите корректный номер телефона")
    private String phoneNumber;

    @Column (name = "name")
    @Size(min = 2, max = 30, message = "Имя не может быть пустым, не менее 2 символов и не более 30 символов")
    private String name;

    @Column (name = "active")
    private boolean active;

    @Column(name = "password", length = 1000)
    @Length(min = 6, message = "Пароль содержать не менее 6 символов")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    private Set <Role> roles = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    @Column(name = "dateOfCreated")
    private LocalDateTime dateOfCreated;

    @Column (name = "activateCode")
    @ToString.Exclude
    private String activateCode;

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public boolean isAdmin(){
        return roles.contains(Role.ROLE_ADMIN);
    }

    //security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    //Getter and Setter

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber, name);
    }
}
