package com.example.sabra_eats.controller;

import com.example.sabra_eats.entity.Order;
import com.example.sabra_eats.entity.MenuItem;
import com.example.sabra_eats.repository.OrderRepository;
import com.example.sabra_eats.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository; // 🌟 කෑම මෙනු එකට අදාළ Repository එක

    // 1. Admin Dashboard (Orders සහ Menu දෙකම පෙන්වීම)
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        // සියලුම ඕඩර්ස් ටික ගන්නවා
        List<Order> allOrders = orderRepository.findAll();
        model.addAttribute("orders", allOrders);

        // 🌟 සියලුම කෑම ජාති ටික ගන්නවා
        List<MenuItem> allItems = menuItemRepository.findAll();
        model.addAttribute("menuItems", allItems);

        return "admin-dashboard";
    }

    // 2. ඕඩර් ස්ටේටස් අප්ඩේට් කිරීම
    @PostMapping("/admin/order/update-status")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId, @RequestParam("status") String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + orderId));
        order.setStatus(status);
        orderRepository.save(order);
        return "redirect:/admin/dashboard";
    }

    // 🌟 3. අලුත් කෑමක් මෙනු එකට එකතු කිරීම (Add Item)
    @PostMapping("/admin/menu/add")
    public String addMenuItem(@RequestParam("name") String name,
                              @RequestParam("price") double price,
                              @RequestParam("shopId") Long shopId) {

        MenuItem newItem = new MenuItem();
        newItem.setName(name);
        newItem.setPrice(price);

        // අන්න අර කලින් කමෙන්ට් එකක් විදිහට තිබ්බ තැනට මේක දාන්න 👇
        newItem.setShopId(shopId);

        menuItemRepository.save(newItem);
        return "redirect:/admin/dashboard";
    }

    // 🌟 4. මෙනු එකෙන් කෑමක් මකා දැමීම (Delete Item)
    @GetMapping("/admin/menu/delete/{id}")
    public String deleteMenuItem(@PathVariable("id") Long id) {
        menuItemRepository.deleteById(id); // ID එකෙන් කෑම එක මකනවා
        return "redirect:/admin/dashboard";
    }
}