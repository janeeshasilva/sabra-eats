package com.example.sabra_eats.repository;

import com.example.sabra_eats.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    // කලින් හදපු Search Bar එකට අදාල කෝඩ් එක
    @Query("SELECT m FROM MenuItem m WHERE LOWER(m.item_name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MenuItem> findByItemNameContainingIgnoreCase(@Param("keyword") String keyword);

    // 🌟 අලුතෙන් එකතු කළ යුතු කොටස (Shop ID එකෙන් කෑම හොයන්න)
    List<MenuItem> findByShopId(Long shopId);
}