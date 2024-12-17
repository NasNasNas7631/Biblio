package com.example.biblio.repo;

import java.sql.Date;
import java.util.List;

import com.example.biblio.obj.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e WHERE CAST(e.id AS string) LIKE %?1%")
    List<Event> search(String keyword);

    @Query("SELECT e FROM Event e WHERE LOWER(e.place) LIKE LOWER(CONCAT('%', :place, '%'))")
    List<Event> findByPlace(@Param("place") String place);

    @Query("SELECT e FROM Event e WHERE e.ddate = :ddate")
    List<Event> findByDate(@Param("ddate") Date ddate);

    @Query("SELECT e FROM Event e WHERE LOWER(e.place) LIKE LOWER(CONCAT('%', :place, '%')) AND e.ddate = :ddate")
    List<Event> findByDateAndPlace(@Param("ddate") Date ddate, @Param("place") String place);

}