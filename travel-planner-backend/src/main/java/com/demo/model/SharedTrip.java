
package com.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shared_trips")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharedTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The trip that is being shared
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    // The user with whom the trip is shared
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_with_user_id", nullable = false)
    private User sharedWith;

    // Optional: Who shared it (can be used for auditing)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_by_user_id")
    private User sharedBy;

    public SharedTrip(Trip trip, User sharedWith, User sharedBy) {
        this.trip = trip;
        this.sharedWith = sharedWith;
        this.sharedBy = sharedBy;
    }

   
   
}
