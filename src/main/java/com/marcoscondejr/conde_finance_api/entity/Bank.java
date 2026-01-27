package com.marcoscondejr.conde_finance_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity()
@Table(name = "bank")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Bank {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String code;

    @Column(nullable = false)
    private Boolean active = true;
}
