package com.trendyol.ecommerce.shopping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLock;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bakar
 * @since 6.07.2019 17:49
 */

@Entity
@Getter
@Setter
@Table
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Category {

    @Id
    @SequenceGenerator(name = "genCategoryId", sequenceName = "SEQ_CATEGORY_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genCategoryId")
    private Long id;

    @Column(nullable = false)
    private final String name;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "category")
    @OptimisticLock(excluded = false)
    private List<Product> products = new ArrayList<>();

}
