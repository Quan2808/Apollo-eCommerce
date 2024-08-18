

class OrderRequest {
  final String userId;
  final String variant;
  final String orderDate;
  final String addressId;
  final int paymentMethodId;
  final int shippingMethodId;
  final int quantity;

  OrderRequest({
    required this.userId,
    required this.variant,
    required this.orderDate,
    required this.addressId,
    required this.paymentMethodId,
    required this.shippingMethodId,
    required this.quantity,
  });

  factory OrderRequest.fromJson(Map<String, dynamic> json) {
    return OrderRequest(
      userId: json['userId'] ?? '',
      variant: json['variantId'] ?? '',
      orderDate: json['orderDate'] ?? '',
      addressId: json['addressId'] ?? '',
      paymentMethodId: json['paymentMethodId'] ?? 0,
      shippingMethodId: json['shippingMethodId'] ?? 0,
      quantity: json['quantity'] ?? 0,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'userId': userId,
      'variantId': variant,
      'orderDate': orderDate,
      'addressId': addressId,
      'paymentMethodId': paymentMethodId,
      'shippingMethodId': shippingMethodId,
      'quantity': quantity,
    };
  }
}