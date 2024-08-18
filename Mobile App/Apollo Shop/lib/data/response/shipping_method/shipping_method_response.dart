class ShippingMethodResponse {
  final int id;
  final String name;
  final double price;

  ShippingMethodResponse({
    required this.id,
    required this.name,
    required this.price,
  });

  factory ShippingMethodResponse.fromJson(Map<String, dynamic> json) {
    return ShippingMethodResponse(
      id: json['id'] ?? 0,
      name: json['name'] ?? '',
      price: (json['price'] as num?)?.toDouble() ?? 0.0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'price': price,
    };
  }
}
