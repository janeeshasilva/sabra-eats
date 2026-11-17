package com.example.sabra_eats.repository;

import com.example.sabra_eats.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    // යූසර්ගේ ID එකෙන් එයාගේ Cart එක හොයාගන්න
    List<Cart> findByUserId(Long userId);

    // එකම කෑම කලින් දාලද බලන්න
    Cart findByUserIdAndMenuItemId(Long userId, Long menuItemId);
}