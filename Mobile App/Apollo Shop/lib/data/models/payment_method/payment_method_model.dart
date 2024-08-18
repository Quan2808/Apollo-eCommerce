import 'package:apolloshop/data/models/user/user_model.dart';

class PaymentMethodModel {
  final int id;
  final UserModel? user;
  final int cardNumber;
  final String? nameOnCard;
  final String? expirationDate;
  final bool? defaultPayment;
  final String type;
  bool selectedPM;

  PaymentMethodModel({
    required this.id,
    required this.cardNumber,
    this.nameOnCard,
    this.expirationDate,
    this.defaultPayment,
    required this.type,
    this.selectedPM = true,
    this.user,
  });

  factory PaymentMethodModel.fromJson(Map<String, dynamic> json) {
    return PaymentMethodModel(
      id: json['id'],
      user: UserModel.fromJson(json['userId'] ?? {}),
      cardNumber: json['cartNumber'] ?? 0,
      nameOnCard: json['nameOnCard'],
      expirationDate: json['expirationDate'],
      defaultPayment: json['defaultPayment'],
      selectedPM: json['defaultPayment'] as bool,
      type: _determineCardType(json['cartNumber'] ?? 0),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'userId': user!.toJson()['id'],
      'cartNumber': cardNumber,
      'nameOnCard': nameOnCard,
      'expirationDate': expirationDate,
      'defaultPayment': defaultPayment,
    };
  }

  static PaymentMethodModel empty() {
    return PaymentMethodModel(
      id: 0,
      cardNumber: 0,
      nameOnCard: '',
      expirationDate: '',
      defaultPayment: false,
      type: '',
      user: UserModel.empty(),
    );
  }

  static String _determineCardType(int cardNumber) {
    if (cardNumber.toString().startsWith('4')) {
      return 'Visa';
    } else if (cardNumber.toString().startsWith('5')) {
      return 'MasterCard';
    } else {
      return 'Unknown';
    }
  }
}
