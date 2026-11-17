package com.example.sabra_eats.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_items") // Database එකේ table එකේ නම
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;

    @Column(name = "shop_id")
    private Long shopId; // මේ කෑම එක අයිති මොන කඩේද කියලා අඳුරගන්න (Foreign Key)

    private String item_name;
    private double price;

    // Getters and Setters
    public Long getItem_id() { return item_id; }
    public void setItem_id(Long item_id) { this.item_id = item_id; }

    public Long getShopId() { return shopId; }
    public void setShopId(Long shopId) { this.shopId = shopId; }

    // මෙන්න මෙතන තමයි වැදගත්ම වෙනස - getName සහ setName ලෙස හදන්න
    public String getName() {
        return item_name;
    }
    public void setName(String item_name) {
        this.item_name = item_name;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}