package com.demo.repository;

import com.demo.model.Trip;
import com.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUser(User user);
}

