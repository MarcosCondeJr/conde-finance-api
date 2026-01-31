package com.marcoscondejr.conde_finance_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    private Long userId;

    private Long bankId;

    private double initialBalance;

    @Column(nullable = false)
    private boolean active = true;
}
