package com.example.buysell.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductDto {
    private String title;
    private String description;
    private String price;
    private String city;
}
