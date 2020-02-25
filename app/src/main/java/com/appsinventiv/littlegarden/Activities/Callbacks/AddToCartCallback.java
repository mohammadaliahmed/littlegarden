package com.appsinventiv.littlegarden.Activities.Callbacks;

import com.appsinventiv.littlegarden.Models.Product;

public interface AddToCartCallback {
    public void onAddToCart(Product product);
    public void onRemoveFromCart(Product product);
}
