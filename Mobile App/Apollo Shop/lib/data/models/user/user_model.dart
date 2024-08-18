class UserModel {
  final String id;
  final String fullName;
  final String email;
  final String phoneNumber;
  final String password;
  final bool enabled;

  UserModel({
    required this.id,
    required this.fullName,
    required this.email,
    required this.phoneNumber,
    required this.password,
    required this.enabled,
  });

  factory UserModel.fromJson(Map<String, dynamic> json) {
    return UserModel(
      id: json['id'].toString(),
      fullName: json['clientName'] ?? '',
      email: json['email'] ?? '',
      phoneNumber: json['phoneNumber'] ?? '',
      password: '',
      enabled: json['enabled'] ?? false,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'clientName': fullName,
      'email': email,
      'phoneNumber': phoneNumber,
      'password': password,
      'enabled': enabled,
    };
  }

  UserModel copyWith({
    String? id,
    String? fullName,
    String? email,
    String? phoneNumber,
    String? password,
    bool? enabled,
  }) {
    return UserModel(
      id: id ?? this.id,
      fullName: fullName ?? this.fullName,
      email: email ?? this.email,
      phoneNumber: phoneNumber ?? this.phoneNumber,
      password: password ?? this.password,
      enabled: enabled ?? this.enabled,
    );
  }

  static UserModel empty() => UserModel(
        id: '',
        fullName: '',
        email: '',
        phoneNumber: '',
        password: '',
        enabled: false,
      );
}
