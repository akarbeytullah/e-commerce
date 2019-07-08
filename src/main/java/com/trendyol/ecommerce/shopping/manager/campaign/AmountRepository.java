package com.trendyol.ecommerce.shopping.manager.campaign;

import com.trendyol.ecommerce.shopping.model.discount.campaign.Campaign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author bakar
 * @since 7.07.2019 22:19
 */
public interface AmountRepository extends CampaignRepository<Campaign> {

    @Query(nativeQuery = true, value = "select max(t.discount) from campaign t where t.quantity < :quantity and t.category_id= :categoryId and t.type ='Amount'")
    Optional<Double> findMaxAmountCampaign(@Param("quantity") Long quantity, @Param("categoryId") Long categoryId);
}
