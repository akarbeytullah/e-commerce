package com.trendyol.ecommerce.shopping.manager.campaign;

import com.trendyol.ecommerce.shopping.model.discount.campaign.Amount;
import com.trendyol.ecommerce.shopping.model.discount.campaign.Rate;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author bakar
 * @since 6.07.2019 22:38
 */
class CampaignManagerTest {

    @Test
    void when_AddCallsForAmountCampaign_Expect_RepositorySaveShouldBeCalled() {

        final CampaignRepository repository = mock(CampaignRepository.class);
        final CampaignManager categoryManager = new CampaignManager(repository);

        Amount amountCampaign = new Amount(2.3, 5L);
        categoryManager.addCampaign(amountCampaign);

        verify(repository).save(amountCampaign);
    }

    @Test
    void when_AddCallsForRateCampaign_Expect_RepositorySaveShouldBeCalled() {

        final CampaignRepository repository = mock(CampaignRepository.class);
        final CampaignManager categoryManager = new CampaignManager(repository);

        Rate amountCampaign = new Rate(2.3, 5L);
        categoryManager.addCampaign(amountCampaign);

        verify(repository).save(amountCampaign);
    }
}