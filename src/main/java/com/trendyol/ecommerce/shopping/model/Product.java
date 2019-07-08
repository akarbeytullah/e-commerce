package com.trendyol.ecommerce.shopping.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author bakar
 * @since 6.07.2019 17:48
 */
@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Product {

    @Id
    @SequenceGenerator(name = "genProductId", sequenceName = "SEQ_PRODUCT_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genProductId")
    private Long id;

    @Column(nullable = false)
    private final String title;

    @Column(nullable = false)
    private final String price;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_PRODUCT_CATEGORY"))
    private final Category category;

}
