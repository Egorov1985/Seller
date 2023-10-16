package com.example.buysell.models;

import com.example.buysell.Annotation.Phone;
import com.example.buysell.models.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "user")
@Data
public class User implements UserDetails {
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
    private Set <Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    @Column(name = "dateOfCreated")
    private Date dateOfCreated;

    @Column (name = "activateCode")
    private String activateCode;

    @PrePersist
    private void init(){
        dateOfCreated = new Date();
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
}
