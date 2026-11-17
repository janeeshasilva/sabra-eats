package com.example.sabra_eats.repository;

import com.example.sabra_eats.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    // කිසිම code එකක් ලියන්න ඕනේ නෑ, JpaRepository එකෙන් ඔක්කොම කරගන්නවා!
}