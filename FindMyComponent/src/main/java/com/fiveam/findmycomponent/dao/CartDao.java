package com.fiveam.findmycomponent.dao;


import com.fiveam.findmycomponent.entity.CartItem;
import java.util.List;

/**
 * CartDao Interface - Defines shopping cart operations
 * Handles all cart operations for logged-in users
 */
public interface CartDao {

    // ========== READ ==========

    /**
     * Get all cart items for a user with product details
     * @param userId the user ID
     * @return List of CartItem objects (empty list if cart is empty)
     */
    List<CartItem> getCartByUserId(int userId);

    /**
     * Get total number of items in cart (sum of quantities)
     * @param userId the user ID
     * @return total item count
     */
    int getCartItemCount(int userId);

    /**
     * Get total price of all items in cart
     * @param userId the user ID
     * @return total price
     */
    double getCartTotal(int userId);

    // ========== WRITE ==========

    /**
     * Add product to cart. If product already exists, quantity is increased.
     * @param userId the user ID
     * @param productId the product ID
     * @param quantity quantity to add (must be > 0)
     * @return true if successful, false otherwise
     */
    boolean addToCart(int userId, int productId, int quantity);

    /**
     * Update quantity of a specific product in cart
     * @param userId the user ID
     * @param productId the product ID
     * @param quantity new quantity (must be > 0)
     * @return true if successful, false otherwise
     */
    boolean updateQuantity(int userId, int productId, int quantity);

    /**
     * Remove a specific product from cart
     * @param userId the user ID
     * @param productId the product ID
     * @return true if successful, false otherwise
     */
    boolean removeFromCart(int userId, int productId);

    /**
     * Clear all items from user's cart
     * @param userId the user ID
     * @return true if successful, false otherwise
     */
    boolean clearCart(int userId);
}