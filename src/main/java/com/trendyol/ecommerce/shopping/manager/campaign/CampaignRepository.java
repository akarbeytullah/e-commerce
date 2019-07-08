package com.trendyol.ecommerce.shopping.manager.campaign;

import com.trendyol.ecommerce.shopping.model.discount.campaign.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author bakar
 * @since 6.07.2019 22:30
 */
public interface CampaignRepository<T extends Campaign> extends JpaRepository<T, Long> {

}
