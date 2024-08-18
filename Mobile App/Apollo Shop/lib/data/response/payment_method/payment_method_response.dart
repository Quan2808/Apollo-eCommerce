class PaymentMethodResponse {
  final int id;
  final int cartNumber;
  final String nameOnCard;
  final String expirationDate;
  final bool defaultPayment;

  PaymentMethodResponse({
    required this.id,
    required this.cartNumber,
    required this.nameOnCard,
    required this.expirationDate,
    required this.defaultPayment,
  });

  factory PaymentMethodResponse.fromJson(Map<String, dynamic> json) {
    return PaymentMethodResponse(
      id: json['id'] ?? 0,
      cartNumber: json['cartNumber'] ?? 0,
      nameOnCard: json['nameOnCard'] ?? '',
      expirationDate: json['expirationDate'] ?? '',
      defaultPayment: json['defaultPayment'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'cartNumber': cartNumber,
      'nameOnCard': nameOnCard,
      'expirationDate': expirationDate,
      'defaultPayment': defaultPayment,
    };
  }
}
