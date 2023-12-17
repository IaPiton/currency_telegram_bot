package com.skillbox.cryptobot.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subscribers")
public class Subscribers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long UUID;
    @Column(name = "id")
    private Long userId;
    private Integer price;

}
