import 'package:apolloshop/data/models/user/user_model.dart';

class AddressModel {
  final String id;
  final String street;
  final UserModel user;
  bool selectedAddress;

  AddressModel({
    required this.id,
    required this.street,
    required this.user,
    this.selectedAddress = true,
  });

  factory AddressModel.fromJson(Map<String, dynamic> json) {
    return AddressModel(
      id: json['id'].toString(),
      street: json['street'].toString(),
      user: UserModel.fromJson(json['user']),
      selectedAddress: json['selectedAddress'] as bool,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      // 'id': id,
      'street': street,
      'userId': user.toJson()['id'],
      // 'selectedAddress': selectedAddress,
    };
  }

  static AddressModel empty() => AddressModel(
        id: '',
        street: '',
        user: UserModel.empty(),
      );

  factory AddressModel.fromSnapshot(Map<String, dynamic> snapshot) {
    return AddressModel(
      id: snapshot['id'].toString(),
      street: snapshot['street']?.toString() ?? '',
      user: UserModel.fromJson(snapshot['user'] ?? {}),
      selectedAddress: snapshot['selectedAddress'] as bool? ?? false,
    );
  }
}
