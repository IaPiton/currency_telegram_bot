package com.skillbox.cryptobot.model;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "subscribers")
public class Subscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private String UUID;
    @Column(name = "id")
    private Long userId;
    private Double price;

}