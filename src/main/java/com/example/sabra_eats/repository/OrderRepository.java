package com.example.sabra_eats.repository;

import com.example.sabra_eats.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // යූසර්ගේ ID එකට අදාල Orders ටික විතරක් හොයලා දෙන මෙතඩ් එක
    List<Order> findByUserId(Long userId);
}