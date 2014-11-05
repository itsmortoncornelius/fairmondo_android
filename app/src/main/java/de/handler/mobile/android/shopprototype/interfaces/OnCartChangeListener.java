package de.handler.mobile.android.shopprototype.interfaces;

import de.handler.mobile.android.shopprototype.rest.json.model.Cart;

/**
 * Notifies implementing class that a product has been removed or added from cart
 */
public interface OnCartChangeListener {
    public void onCartChanged(Cart cart);
}
