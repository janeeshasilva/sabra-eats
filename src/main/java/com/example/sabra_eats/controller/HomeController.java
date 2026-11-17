package com.example.sabra_eats.controller;

import jakarta.servlet.http.HttpSession;
import com.example.sabra_eats.entity.User;
import org.springframework.web.multipart.MultipartFile;
import com.example.sabra_eats.entity.MenuItem;
import com.example.sabra_eats.entity.Shop;
import com.example.sabra_eats.repository.MenuItemRepository;
import com.example.sabra_eats.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Homepage පෙන්වීම
    @GetMapping("/")
    public String home(Model model) {
        List<Shop> shopList = shopRepository.findAll();
        model.addAttribute("shops", shopList);
        return "index";
    }

    // 1. කඩයක් ඇතුලත් කිරීම (Admin Only)
    @PostMapping("/addShop")
    public String addShop(@ModelAttribute Shop shop, @RequestParam("imageFile") MultipartFile imageFile, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/"; // Admin නෙමෙයි නම් ආපහු හරවනවා
        }

        if (!imageFile.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                String uploadDir = "uploads/";
                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
                if (!java.nio.file.Files.exists(uploadPath)) {
                    java.nio.file.Files.createDirectories(uploadPath);
                }
                java.nio.file.Path filePath = uploadPath.resolve(fileName);
                java.nio.file.Files.copy(imageFile.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                shop.setImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        shopRepository.save(shop);
        return "redirect:/";
    }

    // 2. කඩයක් මකා දැමීම (Admin Only)
    @GetMapping("/shop/delete/{id}")
    public String deleteShop(@PathVariable("id") Long shopId, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/";
        }

        List<MenuItem> items = menuItemRepository.findByShopId(shopId);
        menuItemRepository.deleteAll(items);
        shopRepository.deleteById(shopId);
        return "redirect:/";
    }

    // 3. කෑමක් ඇතුලත් කිරීම (Admin Only)
    @PostMapping("/shop/{id}/addItem")
    public String addMenuItem(@PathVariable("id") Long shopId, @ModelAttribute MenuItem menuItem, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/shop/" + shopId;
        }

        menuItem.setShopId(shopId);
        menuItemRepository.save(menuItem);
        return "redirect:/shop/" + shopId;
    }

    // 4. කෑමක් මකා දැමීම (Admin Only)
    @GetMapping("/shop/{shopId}")
    public String showMenu(@PathVariable("shopId") Long shopId,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           Model model) {

        List<MenuItem> items;

        // keyword එකක් ඇවිත් තියෙනවා නම් ඒ අනුව Filter කරනවා
        if (keyword != null && !keyword.isEmpty()) {
            items = menuItemRepository.findByItemNameContainingIgnoreCase(keyword);
        } else {
            // නැත්නම් ඔක්කොම ටික ගන්නවා
            items = menuItemRepository.findAll();
        }

        model.addAttribute("menuItems", items);
        model.addAttribute("keyword", keyword); // ආපහු සර්ච් එක පෙන්නන්න
        model.addAttribute("shopId", shopId);
        return "menu"; // ඔයාගේ menu.html පේජ් එක
    }

    // 5. කඩේක Menu එක පෙන්වීම
    @GetMapping("/shop/{id}")
    public String viewShopMenu(@PathVariable("id") Long id, Model model) {
        Shop shop = shopRepository.findById(id).orElse(null);
        List<MenuItem> menuItems = menuItemRepository.findByShopId(id);

        model.addAttribute("shop", shop);
        model.addAttribute("menuItems", menuItems);

        return "menu";
    }
}