package com.example.biblio.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.biblio.obj.Book;
import com.example.biblio.obj.Event;
import com.example.biblio.obj.User;
import com.example.biblio.service.BookService;
import com.example.biblio.service.EventService;
import com.example.biblio.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private BookService bookservice;

    @Autowired
    private EventService evservice;

    @Autowired
    private UserService userservice;

    // Метод для отображения главной страницы
    @RequestMapping("/") // Корневая страница
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Event> listEvents = evservice.listAll(keyword);
        model.addAttribute("listEvents", listEvents);

        // Получаем текущие роли пользователя и добавляем их в модель
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("roles", roles); // Передаем список ролей в модель

        // Получаем информацию о текущем пользователе
        String username = authentication.getName(); // Получаем имя пользователя
        User user = userservice.findByUsername(username); // Получаем пользователя по имени
        model.addAttribute("user", user); // Передаем объект пользователя в модель

        return "mainpage"; // Возвращаем имя шаблона для главной страницы
    }

    @RequestMapping("/events/search")
    public String searchEvents(Model model,
                               @RequestParam(value = "place", required = false) String place,
                               @RequestParam(value = "ddate", required = false) String ddateStr) {
        List<Event> listEvents;
        Date ddate = null;

        // Преобразуем строку в объект Date, если она не пустая
        if (ddateStr != null && !ddateStr.isEmpty()) {
            ddate = Date.valueOf(ddateStr); // Преобразование строки в Date
        }

        // Выполняем поиск на основе предоставленных параметров
        if (place != null && !place.isEmpty() && ddate != null) {
            listEvents = evservice.searchEvent(place, ddate);
        } else if (place != null && !place.isEmpty()) {
            listEvents = evservice.searchEvent(place, null);
        } else if (ddate != null) {
            listEvents = evservice.searchEvent(null, ddate);
        } else {
            listEvents = evservice.listAll(null); // Если ничего не указано, возвращаем все события
        }

        model.addAttribute("listEvents", listEvents); // Добавляем события в модель
        // Получаем текущие роли пользователя и добавляем их в модель
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("roles", roles); // Передаем список ролей в модель

        // Получаем информацию о текущем пользователе
        String username = authentication.getName(); // Получаем имя пользователя
        User user = userservice.findByUsername(username); // Получаем пользователя по имени
        model.addAttribute("user", user); // Передаем объект пользователя в модель

        return "mainpage"; // Возвращаем имя шаблона для главной страницы
    }

    // Event-related mappings
    @RequestMapping("/events/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showNewEventForm(Model model) {
        Event event = new Event(); // Создаем новый объект события
        List<Event> events = evservice.listAll(null); // Получаем список всех мероприятий

        // Получаем список используемых аудиторий
        List<String> usedAuditoriums = events.stream()
                .map(Event::getPlace)
                .collect(Collectors.toList());

        model.addAttribute("event", event); // Добавляем новый объект события в модель
        model.addAttribute("usedAuditoriums", usedAuditoriums); // Добавляем список используемых аудиторий в модель

        return "new_event"; // Возвращаем шаблон для добавления события
    }

    @RequestMapping(value = "/events/save", method = RequestMethod.POST)
    public String saveEvent(@ModelAttribute("event") Event event) {
        evservice.save_event(event); // Сохраняем событие в базе данных
        return "redirect:/"; // Перенаправляем на главную страницу после сохранения
    }

    @RequestMapping("/events/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showEditEventForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_event");  // Шаблон редактирования события
        Event event = evservice.get_event(id); // Получаем событие по ID для редактирования
        List<Event> events = evservice.listAll(null); // Получаем список всех мероприятий

        // Получаем список используемых аудиторий
        List<String> usedAuditoriums = events.stream()
                .map(Event::getPlace)
                .collect(Collectors.toList());

        mav.addObject("event", event);
        mav.addObject("usedAuditoriums", usedAuditoriums);

        return mav; // Возвращаем модель и представление
    }

    @RequestMapping("/events/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteEvent(@PathVariable(name = "id") Long id) {
        evservice.delete_event(id); // Удаляем событие по ID
        return "redirect:/"; // Перенаправляем на главную страницу после удаления
    }

    // Метод для отображения страницы книг
    @RequestMapping("/bookpage")
    public String viewBooksPage(Model model, @Param("keyword") String keyword, @Param("column") String column) {
        List<Book> listBooks;

        // Fetching books based on keyword and column
        if (keyword != null && !keyword.isEmpty() && column != null) {
            listBooks = bookservice.findByColumn(keyword, column);
        } else {
            listBooks = bookservice.listAll(null);
        }

        model.addAttribute("listBooks", listBooks);
        model.addAttribute("keyword", keyword);

        // Get current authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Get username
            User user = userservice.findByUsername(username); // Get User object

            if (user != null) {
                model.addAttribute("user", user); // Add user to model
            }
        }

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        model.addAttribute("roles", roles); // Add roles to model

        return "bookpage"; // Return view name
    }

    // Book-related mappings
    @RequestMapping("/books/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String showNewBookForm(Model model) {
        Book book = new Book(); // Создаем новый объект книги
        model.addAttribute("book", book); // Добавляем его в модель
        return "new_book"; // Возвращаем шаблон для добавления книги
    }

    @RequestMapping(value = "/books/save", method = RequestMethod.POST)
    public String saveBook(@ModelAttribute("book") Book book) {
        bookservice.save(book); // Сохраняем книгу в базе данных
        return "redirect:/bookpage"; // Перенаправляем на главную страницу после сохранения
    }

    @RequestMapping("/books/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView showEditBookForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_book");  // Шаблон редактирования книги
        Book book = bookservice.get(id); // Получаем книгу по ID для редактирования
        mav.addObject("book", book);
        return mav; // Возвращаем модель и представление
    }

    @RequestMapping("/books/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBook(@PathVariable(name = "id") Long id) {
        bookservice.delete(id); // Удаляем книгу по ID
        return "redirect:/bookpage"; // Перенаправляем на главную страницу после удаления
    }

    @RequestMapping("/author")
    public String viewAuthorPage() {
        return "author";  // Возвращаем имя шаблона для отображения авторов или информации о них.
    }

    @RequestMapping("/userpage/{username}")
    public ModelAndView showAccount(@PathVariable(name = "username") String username) {
        ModelAndView mav = new ModelAndView("userpage");  // Шаблон для отображения информации о пользователе
        User user = userservice.findByUsername(username);  // Получаем пользователя по имени
        mav.addObject("user", user);  // Передаем объект пользователя в модель
        return mav;  // Возвращаем модель и представление
    }

    @RequestMapping(value = "/userpage/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        userservice.save_user(user);  // Сохраняем или обновляем пользователя
        return "redirect:/userpage/" + user.getUsername();  // Перенаправляем на страницу аккаунта после сохранения
    }

    @RequestMapping("/userlist")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewUsersPage(Model model, @Param("keyword") String keyword, @Param("column") String column) {
        List<User> listUsers;
        if (keyword != null && !keyword.isEmpty()) {
            listUsers = userservice.findByColumn(keyword);
        } else {
            listUsers = userservice.listAll(null);
        }
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("keyword", keyword);
        return "userlist";
    }

    @RequestMapping("/userlist/search")
    public String searchUsers(Model model, @RequestParam(value="username", required = false) String username) {
        List<User> listUsers;
        if (username != null && !username.isEmpty()) {
            listUsers = userservice.searchUsers(username);
        } else {
            listUsers = userservice.listAll(null);
        }
        model.addAttribute("listUsers", listUsers);
        return "userlist";
    }

    @GetMapping("/ddate")
    @ResponseBody
    public Map<String, Object> getIssueData(@Param("keyword") String keyword) {
        List<User> theatres = userservice.listAll(keyword);

        Map<String, Long> issueCountByDate = theatres.stream()
                .filter(user -> user.getDdate() != null)
                .collect(Collectors.groupingBy(
                        user -> user.getDdate().toString(),
                        Collectors.counting()
                ));

        List<String> dates = new ArrayList<>(issueCountByDate.keySet());
        List<Long> counts = dates.stream()
                .map(issueCountByDate::get)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("dates", dates);
        response.put("counts", counts);

        return response;
    }
}