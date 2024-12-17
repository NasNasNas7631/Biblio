package com.example.biblio.service;

import com.example.biblio.obj.Book;
import com.example.biblio.obj.BookOrder;
import com.example.biblio.repo.BookOrderRepository;
import com.example.biblio.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookOrderService {

    @Autowired
    private BookOrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

        public List<BookOrder> getAllOrders() {
        return (List<BookOrder>) orderRepository.findAll();
    }

    public boolean createOrder(String username, Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setStatus("Заказана");
            bookRepository.save(book);

            BookOrder order = new BookOrder();
            order.setUsername(username);
            order.setBook_id(bookId);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    public List<BookOrder> getOrdersByUsername(String username) {
        List<BookOrder> orders = orderRepository.findByUsername(username);

        for (BookOrder order : orders) {
            Optional<Book> optionalBook = bookRepository.findById(order.getBook_id());
            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                order.setName(book.getName());
                order.setAuthor(book.getAuthor());
            }
        }

        return orders;
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public BookOrder findByBookId(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
