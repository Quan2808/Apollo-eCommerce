import 'package:apolloshop/data/models/product/product_model.dart';

class VariantModel {
  int id;
  String name;
  String skuCode;
  int quantity;
  double weight;
  double price;
  double salePrice;
  String img;
  bool isDeleted;
  ProductModel? product;

  VariantModel({
    required this.id,
    required this.name,
    required this.skuCode,
    required this.quantity,
    required this.weight,
    required this.price,
    required this.salePrice,
    required this.img,
    required this.isDeleted,
    this.product,
  });

  static VariantModel empty() {
    return VariantModel(
      id: 0,
      name: '',
      skuCode: '',
      quantity: 0,
      weight: 0.0,
      price: 0.0,
      salePrice: 0.0,
      img: '',
      isDeleted: false,
      product: null,
    );
  }

  factory VariantModel.fromJson(Map<String, dynamic> json) {
    String img = json['img'] ?? '';

    if (img.contains('localhost')) {
      img = img.replaceFirst('localhost', '10.0.2.2');
    }

    return VariantModel(
      id: json['id'] ?? 0,
      name: json['name'] ?? '',
      skuCode: json['skuCode'] ?? '',
      quantity: json['stockQuantity'] ?? 0,
      weight: (json['weight'] ?? 0.0).toDouble(),
      price: (json['price'] ?? 0.0).toDouble(),
      salePrice: (json['salePrice'] ?? 0.0).toDouble(),
      img: img,
      isDeleted: json['isDeleted'] ?? false,
      product: json['product'] != null
          ? ProductModel.fromJson(json['product'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'skuCode': skuCode,
      'stockQuantity': quantity,
      'weight': weight,
      'price': price,
      'salePrice': salePrice,
      'img': img,
      'isDeleted': isDeleted,
      'product': product?.toJson(),
    };
  }
}
