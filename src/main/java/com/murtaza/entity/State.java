package com.murtaza.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "STATE_MASTER")
@Setter
@Getter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stateId;

    private String stateName;
    @ManyToOne
    @JoinColumn(name = "country_fk")
    private Country country;
}
