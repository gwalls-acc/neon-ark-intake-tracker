package org.example.neonarkintaketracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Entity
@Table(name = "feeding_schedules")
@Getter @Setter
public class FeedingSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many schedules can belong to one creature
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creature_id", nullable = false)
    private Creature creature;

    // LocalTime maps perfectly to the SQL TIME type (HH:mm:ss)
    @Column(name = "feeding_time", nullable = false)
    private LocalTime feedingTime;

    public FeedingSchedule() {}

    public FeedingSchedule(Creature creature, LocalTime feedingTime) {
        this.creature = creature;
        this.feedingTime = feedingTime;
    }
}