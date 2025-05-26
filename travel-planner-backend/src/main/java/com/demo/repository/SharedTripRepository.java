package com.demo.repository;

import com.demo.model.SharedTrip;
import com.demo.model.User;
import com.demo.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SharedTripRepository extends JpaRepository<SharedTrip, Long> {

    List<SharedTrip> findBySharedWith(User user);

    Optional<SharedTrip> findByTripAndSharedWith(Trip trip, User sharedWith);
    List<SharedTrip> findBySharedBy(User sharedBy);

}

