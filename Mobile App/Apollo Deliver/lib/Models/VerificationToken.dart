import 'package:apollodeliver/Models/Shipper.dart';
import 'package:apollodeliver/Models/User.dart';

class VerificationToken {
  int? id;
  String? token;
  User? user;
  Shipper? shipper;
  DateTime? expiryDate;

  VerificationToken({
    this.id,
    this.token,
    this.user,
    this.shipper,
    this.expiryDate,
  });

  factory VerificationToken.fromJson(Map<String, dynamic> json) {
    return VerificationToken(
      id: json['id'],
      token: json['token'],
      user: json['user'] != null ? User.fromJson(json['user']) : null,
      shipper: json['shipper'] != null ? Shipper.fromJson(json['shipper']) : null,
      expiryDate: json['expiryDate'] != null ? DateTime.parse(json['expiryDate']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'token': token,
    'user': user?.toJson(),
    'shipper': shipper?.toJson(),
    'expiryDate': expiryDate?.toIso8601String(),
  };
}
