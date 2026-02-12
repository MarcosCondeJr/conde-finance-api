package com.marcoscondejr.conde_finance_api.entity;

import com.marcoscondejr.conde_finance_api.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;
}
