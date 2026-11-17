package com.example.sabra_eats.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders") // Database එකේ හැදෙන Table එක
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    private Long userId; // කාගේ Order එකක්ද කියලා අඳුරගන්න
    private String itemName;
    private double price;
    private int quantity;
    private String status; // Pending ද Complete ද කියලා දැනගන්න

    // 🌟 අලුතින් එකතු කරපු Field එක (Database එකේ 'total_price' තීරුවට අදාළව)
    private double totalPrice;

    // Getters and Setters
    public Long getOrder_id() { return order_id; }
    public void setOrder_id(Long order_id) { this.order_id = order_id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // 🌟 අලුතින් එකතු කරපු Getter සහ Setter
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}