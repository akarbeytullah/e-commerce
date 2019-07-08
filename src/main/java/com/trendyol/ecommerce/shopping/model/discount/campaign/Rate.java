package com.trendyol.ecommerce.shopping.model.discount.campaign;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author bakar
 * @since 6.07.2019 18:05
 */

@Entity
@DiscriminatorValue("Rate")
public class Rate extends Campaign {

    public Rate(Double discount, Long quantity) {
        super(discount, quantity);
    }
}
