package com.trendyol.ecommerce.shopping.model.discount.coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author bakar
 * @since 6.07.2019 18:16
 */

@Entity
@Table
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Coupon {

    @Id
    @SequenceGenerator(name = "genCouponId", sequenceName = "SEQ_COUPON_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genCouponId")
    private Long id;

    @Column(nullable = false)
    private final Long amount;

    @Column(nullable = false)
    private final Long discount;


}
