package com.murtaza.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CITY_MASTER")
@Setter
@Getter
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;

    private String cityName;
    @ManyToOne
    @JoinColumn(name = "state_fk")
    private State state;
}
