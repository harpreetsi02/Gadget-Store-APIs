package com.backend.gadgetstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String country;
}
