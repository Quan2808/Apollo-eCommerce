class OrderItemRequest {
  final int variantId;
  final int quantity;
  final double price;

  OrderItemRequest({
    required this.variantId,
    required this.quantity,
    required this.price,
  });

  factory OrderItemRequest.fromJson(Map<String, dynamic> json) {
    return OrderItemRequest(
      variantId: json['variantId'] ?? 0,
      quantity: json['quantity'] ?? 0,
      price: (json['price'] as num?)?.toDouble() ?? 0.0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'variantId': variantId,
      'quantity': quantity,
      'price': price,
    };
  }
}
