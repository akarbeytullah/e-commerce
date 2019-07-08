package com.trendyol.ecommerce.shopping.model.discount.campaign;

import com.trendyol.ecommerce.shopping.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author bakar
 * @since 6.07.2019 18:00
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@RequiredArgsConstructor
@Getter
@NoArgsConstructor(force = true)
public abstract class Campaign {

    @Id
    @SequenceGenerator(name = "genCampaignId", sequenceName = "SEQ_CAMPAIGN_ID", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genCampaignId")
    private Long id;

    @Column(nullable = false)
    private final Double discount;

    @Column(nullable = false)
    private final Long quantity;

    @Setter
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_CAMPAIGN_CATEGORY"))
    private Category category;

}
