class PricingCalculator {
  /// -- Calculate the total price including shipping cost
  static double calculateTotalPrice(double productPrice, String destination) {
    double shippingCost = _getShippingCost(destination);
    double totalPrice = productPrice + shippingCost;
    return totalPrice;
  }

  /// -- Get the shipping cost as a formatted string
  static String getFormattedShippingCost(String destination) {
    double shippingCost = _getShippingCost(destination);
    return shippingCost.toStringAsFixed(2);
  }

  /// -- Private method to retrieve shipping cost based on destination
  static double _getShippingCost(String destination) {
    // Lookup the shipping cost for the given destination using a shipping rate API.
    // Calculate the shipping cost based on various factors like distance, weight, etc.
    return 5.00; // Example shipping cost of $5
  }

  /// -- Sum all cart values and return total amount
  // static double calculateCartTotal(CartModel cart) {
  //   return cart.items.map((e) => e.price).fold(0,
  //       (previousPrice, currentPrice) => previousPrice + (currentPrice ?? 0));
  // }
}
