package com.example.biblio.controller;

import com.example.biblio.obj.BookOrder;
import com.example.biblio.service.BookOrderService;
import com.example.biblio.service.UserService; // Import UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserOrderController {

    @Autowired
    private BookOrderService bookOrderService;

    @Autowired
    private UserService userService;

    @GetMapping("/orders")
    public String getUserOrders(Model model) {
        String username = userService.getCurrentUsername();

        List<BookOrder> orders = bookOrderService.getOrdersByUsername(username);
        model.addAttribute("orders", orders);
        model.addAttribute("username", username);
        return "orders";
    }
}