package com.trendyol.ecommerce.shopping.web.campaign;

import com.trendyol.ecommerce.shopping.manager.campaign.CampaignManagement;
import com.trendyol.ecommerce.shopping.manager.category.CategoryManagement;
import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.discount.campaign.Amount;
import com.trendyol.ecommerce.shopping.model.discount.campaign.Rate;
import com.trendyol.ecommerce.shopping.web.ValueNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author bakar
 * @since 6.07.2019 19:08
 */

@RestController
@RequestMapping("campaign")
@CrossOrigin
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignManagement campaignManagement;

    private final CategoryManagement categoryManagement;

    @PostMapping(value = "/add/amount/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addAmountCampaign(@PathVariable String categoryId, @RequestBody Map<String, String> body) {

        final Category category = getCategory(categoryId);
        campaignManagement.addCampaign(createAmountCampaign(body, category));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/add/rate/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addRateCampaign(@PathVariable String categoryId, @RequestBody Map<String, String> body) {

        final Category category = getCategory(categoryId);
        campaignManagement.addCampaign(createRateCampaign(body, category));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Amount createAmountCampaign(@RequestBody Map<String, String> body, Category category) {

        final Double discount = Double.parseDouble(body.get("discount"));
        final Long quantity = Long.parseLong(body.get("quantity"));

        final Amount campaign = new Amount(discount, quantity);
        campaign.setCategory(category);
        return campaign;
    }

    private Rate createRateCampaign(@RequestBody Map<String, String> body, Category category) {

        final Double discount = Double.parseDouble(body.get("discount"));
        final Long quantity = Long.parseLong(body.get("quantity"));

        final Rate campaign = new Rate(discount, quantity);
        campaign.setCategory(category);
        return campaign;
    }

    private Category getCategory(String categoryId) {
        return categoryManagement.getCategory(Long.valueOf(categoryId))
                .orElseThrow(() -> new ValueNotFoundException("Category is not found!"));
    }


}
