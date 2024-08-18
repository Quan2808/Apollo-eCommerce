class OrderItemModel {
  final String variantName;
  final int quantity;
  final double price;

  OrderItemModel({
    required this.variantName,
    required this.quantity,
    required this.price,
  });

  factory OrderItemModel.fromJson(Map<String, dynamic> json) {
    return OrderItemModel(
      variantName: json['variantName'] ?? '',
      quantity: json['quantity'] ?? 0,
      price: (json['price'] as num?)?.toDouble() ?? 0.0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'variantName': variantName,
      'quantity': quantity,
      'price': price,
    };
  }
}
