class AddressResponse {
  final int id;
  final String? district;
  final String? ward;
  final String? city;
  final String? street;

  AddressResponse({
    required this.id,
    this.district,
    this.ward,
    this.city,
    this.street,
  });

  factory AddressResponse.fromJson(Map<String, dynamic> json) {
    return AddressResponse(
      id: json['id'] ?? 0,
      district: json['district'],
      ward: json['ward'],
      city: json['city'],
      street: json['street'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'district': district,
      'ward': ward,
      'city': city,
      'street': street,
    };
  }
}
