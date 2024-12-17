package com.example.biblio.controller;

import com.example.biblio.obj.BookOrder;
import com.example.biblio.service.BookOrderService;
import com.example.biblio.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class BookOrderController {

    @Autowired
    private BookOrderService orderService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody BookOrder orderRequest) {
        String username = orderRequest.getUsername();
        Long bookId = orderRequest.getBook_id();

        boolean isOrderCreated = orderService.createOrder(username, bookId);

        if (isOrderCreated) {
            return ResponseEntity.ok("Order created successfully");
        } else {
            return ResponseEntity.status(400).body("Failed to create order");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        Optional<BookOrder> orderOptional = Optional.ofNullable(orderService.findByBookId(id)); // Fetching by ID directly

        if (orderOptional.isPresent()) {
            BookOrder order = orderOptional.get();
            Long bookId = order.getBook_id(); // Get associated book ID

            boolean isOrderDeleted = orderService.deleteOrder(id); // Proceed to delete the order

            if (isOrderDeleted) {
                bookService.updateStatus(bookId, "Свободна"); // Update the book status to 'Свободна'
                return ResponseEntity.ok("Order deleted successfully and book status updated to 'Свободна'");
            } else {
                return ResponseEntity.status(500).body("Failed to delete the order");
            }
        } else {
            return ResponseEntity.status(404).body("Order not found");
        }
    }
}
