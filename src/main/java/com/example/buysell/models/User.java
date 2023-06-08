package com.example.buysell.models;

import com.example.buysell.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Entity
@Table
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Email
    @Column (name = "email", unique = true)
    private String email;

    @Column (name = "phone_Number")
    private String phoneNumber;

    @Column (name = "name")
    private String name;

    @Column (name = "active")
    private boolean active;

    @OneToOne (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name = "image_id")
    private Image avatar;

    @Column(name = "password", length = 1000)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set <Role> roles = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    @Column(name = "dateOfCreated")
    private Date dateOfCreated;

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
        return active;
    }
}
