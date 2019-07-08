package com.trendyol.ecommerce.shopping.model.discount.campaign;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author bakar
 * @since 6.07.2019 18:06
 */

@Entity
@DiscriminatorValue("Amount")
public class Amount extends Campaign {

    public Amount(Double discount, Long quantity) {
        super(discount, quantity);
    }
}
