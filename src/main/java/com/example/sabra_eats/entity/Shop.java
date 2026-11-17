package com.example.sabra_eats.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shop_id;

    private String shop_name;
    private String location;
    private String image; // අලුතින් එකතු කලා

    // Getters and Setters
    public Long getShop_id() { return shop_id; }
    public void setShop_id(Long shop_id) { this.shop_id = shop_id; }

    public String getShop_name() { return shop_name; }
    public void setShop_name(String shop_name) { this.shop_name = shop_name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}