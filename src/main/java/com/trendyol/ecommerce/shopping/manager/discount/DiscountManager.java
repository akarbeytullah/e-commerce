package com.trendyol.ecommerce.shopping.manager.discount;

import com.trendyol.ecommerce.shopping.manager.campaign.AmountRepository;
import com.trendyol.ecommerce.shopping.manager.campaign.RateRepository;
import com.trendyol.ecommerce.shopping.model.Category;
import com.trendyol.ecommerce.shopping.model.ShoppingCart;
import com.trendyol.ecommerce.shopping.model.ShoppingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author bakar
 * @since 7.07.2019 18:31
 */
@Service
@RequiredArgsConstructor
public class DiscountManager implements DiscountManagement {

    private final AmountRepository amountRepository;
    private final RateRepository rateRepository;

    @Override
    public Double calculate(ShoppingCart shoppingCart) {

        final HashMap<Category, Double> categoryDiscountMap = new HashMap<>();

        shoppingCart.getCategoryItems().forEach((category, shoppingItems) -> {

            final Double amountCampaignPrice = getAmountCampaignPrice(category, shoppingItems);
            final Double rateCampaignPrice = getRateCampaignPrice(category, shoppingItems);

            if (rateCampaignPrice > amountCampaignPrice) {
                categoryDiscountMap.put(category, rateCampaignPrice);
            } else {
                categoryDiscountMap.put(category, amountCampaignPrice);
            }
        });

        return categoryDiscountMap.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();
    }

    private Double getAmountCampaignPrice(Category category, ArrayList<ShoppingItem> items) {

        return amountRepository.findMaxAmountCampaign(countQuantity(items), category.getId())
                .orElse(0.0);
    }

    private Double getRateCampaignPrice(Category key, ArrayList<ShoppingItem> value) {

        return getRateCampaign(key, value)
                .map(rate -> (getCategoryPrice(value) * rate) / 100)
                .orElse(0.0);
    }

    private Optional<Double> getRateCampaign(Category category, ArrayList<ShoppingItem> items) {

        return rateRepository.findMaxRateCampaign(countQuantity(items), category.getId());
    }

    private Long getCategoryPrice(ArrayList<ShoppingItem> items) {

        return items
                .stream()
                .mapToLong(item -> item.getQuantity() * Long.valueOf(item.getProduct().getPrice()))
                .sum();
    }

    private Long countQuantity(ArrayList<ShoppingItem> items) {
        return items.stream().mapToLong(ShoppingItem::getQuantity).sum();
    }

}
