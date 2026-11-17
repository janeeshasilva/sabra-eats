package com.example.sabra_eats.controller;

import com.example.sabra_eats.entity.Order;
import com.example.sabra_eats.repository.OrderRepository;
import com.example.sabra_eats.entity.Cart;
import com.example.sabra_eats.entity.MenuItem;
import com.example.sabra_eats.entity.User;
import com.example.sabra_eats.repository.CartRepository;
import com.example.sabra_eats.repository.MenuItemRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    // 1. Cart එකට කෑම එකතු කිරීම
    @GetMapping("/shop/{shopId}/item/{itemId}/addToCart")
    public String addToCart(@PathVariable("shopId") Long shopId, @PathVariable("itemId") Long itemId, HttpSession session) {

        // යූසර් ලොග් වෙලාද බලනවා
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login"; // ලොග් වෙලා නැත්නම් login page එකට යවනවා
        }

        // කෑම එකේ විස්තර ගන්නවා
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + itemId));

        // Cart එකට අලුත් අයිටම් එකක් හදනවා
        Cart cartItem = new Cart();
        cartItem.setUserId(user.getId()); // ලොග් වෙලා ඉන්න කෙනාගේ ID එක
        cartItem.setMenuItemId(menuItem.getItem_id());
        cartItem.setItemName(menuItem.getName());
        cartItem.setPrice(menuItem.getPrice());
        cartItem.setQuantity(1);

        // Database එකට සේව් කරනවා
        cartRepository.save(cartItem);
        return "redirect:/shop/" + shopId; // ආපහු කඩේ මෙනු එකටම යනවා
    }

    // 2. Cart Page එක පෙන්වීම
    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {

        // යූසර් ලොග් වෙලාද බලනවා
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        // මේ යූසර්ගේ Cart එකේ තියෙන දේවල් විතරක් Database එකෙන් ගන්නවා
        List<Cart> cartItems = cartRepository.findByUserId(user.getId());

        // මුළු ගාන (Total Price) එකතු කරනවා
        double grandTotal = cartItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();

        // HTML එකට Data යවනවා
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", grandTotal);
        return "cart"; // cart.html පේජ් එක ලෝඩ් කරනවා
    }

    // 3. Checkout කිරීම (Order එක දැම්මට පස්සේ Cart එක හිස් කිරීම)
    @Transactional
    @GetMapping("/cart/checkout")
    public String checkout(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        List<Cart> cartItems = cartRepository.findByUserId(user.getId());

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        for (Cart item : cartItems) {
            Order order = new Order();
            order.setUserId(user.getId());
            order.setItemName(item.getItemName());
            order.setPrice(item.getPrice());
            order.setQuantity(item.getQuantity());
            order.setStatus("PENDING");

            // 🌟 අලුතින් මේ පේළිය විතරක් එකතු කරන්න 🌟
            order.setTotalPrice(item.getPrice() * item.getQuantity());

            orderRepository.save(order);
        }

        cartRepository.deleteAll(cartItems);

        return "redirect:/cart?ordered=true";
    }

    // 4. My Orders පිටුව පෙන්වීම
    @GetMapping("/orders")
    public String viewMyOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user != null) {
            // යූසර්ගේ ID එකට අදාල Orders ටික OrderRepository හරහා ගන්නවා
            List<Order> myOrders = orderRepository.findByUserId(user.getId());
            model.addAttribute("myOrders", myOrders);
            return "orders"; // orders.html ෆයිල් එකට යනවා
        } else {
            return "redirect:/login"; // ලොග් වෙලා නැත්නම් ලොගින් එකට යවනවා
        }
    }

    // 5. Cart එකෙන් එක කෑමක් පමණක් මකා දැමීම
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user != null) {
            cartRepository.deleteById(id); // අදාල කෑම එක Cart එකෙන් මකනවා
        }
        return "redirect:/cart"; // ආපහු Cart එකටම එනවා
    }
}