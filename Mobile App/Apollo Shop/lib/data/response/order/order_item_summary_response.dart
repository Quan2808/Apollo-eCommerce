class OrderItemSummaryResponse {
  final int id;
  final String productName;
  final int quantity;
  final double price;

  OrderItemSummaryResponse({
    required this.id,
    required this.productName,
    required this.quantity,
    required this.price,
  });

  factory OrderItemSummaryResponse.fromJson(Map<String, dynamic> json) {
    return OrderItemSummaryResponse(
      id: json['id'] ?? 0,
      productName: json['productName'] ?? '',
      quantity: json['quantity'] ?? 0,
      price: (json['price'] as num?)?.toDouble() ?? 0.0,
    );
  }
}
