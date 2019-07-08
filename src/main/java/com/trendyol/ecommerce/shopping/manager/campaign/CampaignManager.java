package com.trendyol.ecommerce.shopping.manager.campaign;

import com.trendyol.ecommerce.shopping.model.discount.campaign.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author bakar
 * @since 6.07.2019 22:29
 */
@Service
@RequiredArgsConstructor
public class CampaignManager implements CampaignManagement {

    private final CampaignRepository campaignRepository;

    @Override
    public void addCampaign(Campaign campaign) {
        campaignRepository.save(campaign);
    }
}
