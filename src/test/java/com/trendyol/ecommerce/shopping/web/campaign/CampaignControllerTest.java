package com.trendyol.ecommerce.shopping.web.campaign;

import com.trendyol.ecommerce.shopping.manager.campaign.CampaignManagement;
import com.trendyol.ecommerce.shopping.manager.category.CategoryManagement;
import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.discount.campaign.Amount;
import com.trendyol.ecommerce.shopping.model.discount.campaign.Campaign;
import com.trendyol.ecommerce.shopping.model.discount.campaign.Rate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bakar
 * @since 6.07.2019 23:07
 */

@ExtendWith(MockitoExtension.class)
class CampaignControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CampaignManagement campaignManagement;

    @Mock
    private CategoryManagement categoryManagement;

    @BeforeEach
    void setUp() {

        CampaignController controller = new CampaignController(campaignManagement, categoryManagement);
        mockMvc = MockMvcBuilders.
                standaloneSetup(controller)
                .setControllerAdvice(CampaignRestResponseEntityExceptionHandler.class)
                .build();
    }


    @Test
    void when_addCampaignCallsForAmountCampaign_Expect_ProductManagementShouldSaveCampaign() throws Exception {

        final String discount = "50.0";
        final String quantity = "10";
        String requestJson = "{\"discount\":\"" + discount + "\", \"quantity\":\"" + quantity + "\"}";

        final String categoryId = "1";

        final Category categoryOfCampaign = new Category("Food");
        final Optional<Category> categoryOfFood = Optional.of(categoryOfCampaign);
        when(categoryManagement.getCategory(Long.valueOf(categoryId))).thenReturn(categoryOfFood);

        mockMvc.perform(post("/campaign/add/amount/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful());

        ArgumentCaptor<Campaign> campaignCaptor = ArgumentCaptor.forClass(Campaign.class);
        verify(campaignManagement).addCampaign(campaignCaptor.capture());

        final Campaign campaign = campaignCaptor.getValue();

        assertTrue(Amount.class.isInstance(campaign), "Campaign type should be Amount!");

        assertEquals(categoryOfCampaign, campaign.getCategory(), "category of campaign should match!");
        assertEquals(Double.parseDouble(discount), campaign.getDiscount().doubleValue(), "Discount value should match!");
        assertEquals(Long.parseLong(quantity), campaign.getQuantity().longValue(), "Discount value should match!");
    }

    @Test
    void when_addCampaignCallsForRateCampaign_Expect_ProductManagementShouldSaveCampaign() throws Exception {

        final String discount = "50.0";
        final String quantity = "10";
        String requestJson = "{\"discount\":\"" + discount + "\", \"quantity\":\"" + quantity + "\"}";

        final String categoryId = "1";

        final Category categoryOfCampaign = new Category("Food");
        final Optional<Category> categoryOfFood = Optional.of(categoryOfCampaign);
        when(categoryManagement.getCategory(Long.valueOf(categoryId))).thenReturn(categoryOfFood);

        mockMvc.perform(post("/campaign/add/rate/" + categoryId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is2xxSuccessful());

        ArgumentCaptor<Campaign> campaignCaptor = ArgumentCaptor.forClass(Campaign.class);
        verify(campaignManagement).addCampaign(campaignCaptor.capture());

        final Campaign campaign = campaignCaptor.getValue();

        assertTrue(Rate.class.isInstance(campaign), "Campaign type should be Amount!");

        assertEquals(categoryOfCampaign, campaign.getCategory(), "category of campaign should match!");
        assertEquals(Double.parseDouble(discount), campaign.getDiscount().doubleValue(), "Discount value should match!");
        assertEquals(Long.parseLong(quantity), campaign.getQuantity().longValue(), "Discount value should match!");
    }

}