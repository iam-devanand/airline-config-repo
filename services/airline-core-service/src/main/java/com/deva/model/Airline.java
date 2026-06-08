package com.deva.model;

import com.deva.embeddable.Support;
import com.deva.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "airlines")
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String iataCode;

    @Column(length = 50, nullable = false, unique = true)
    private String icaoCode;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private Long ownerId;

    private String alias;

    private String logoUrl;
    private String website;

    @Enumerated(EnumType.STRING)
    private AirlineStatus status =  AirlineStatus.ACTIVE;

    private String alliance;

    private Long headquartersCityId;
    private Long updatedById;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    private Support support;



}
