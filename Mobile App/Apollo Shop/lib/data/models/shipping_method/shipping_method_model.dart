class ShippingMethodModel {
  final int id;
  final String name;
  final double price;
  bool isSelected;

  ShippingMethodModel({
    required this.id,
    required this.name,
    required this.price,
    this.isSelected = false,
  });

  factory ShippingMethodModel.fromJson(Map<String, dynamic> json) {
    return ShippingMethodModel(
      id: json['id'] ?? 0,
      name: json['name'] ?? '',
      price: json['price'] ?? 0.0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'price': price,
    };
  }

  static ShippingMethodModel empty() {
    return ShippingMethodModel(
      id: 0,
      name: '',
      price: 0.0,
    );
  }
}
