package com.example.biblio.repo;

import com.example.biblio.obj.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT t FROM User t WHERE LOWER(t.username) LIKE LOWER(CONCAT('%', :username, '%'))")
     User findByUsername(@Param("username") String name);

    @Query("SELECT t FROM User t WHERE LOWER(t.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    List<User> findListusername(@Param("username") String name);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<User> search(@Param("keyword") String keyword);

    boolean existsByUsername(String username);
}
