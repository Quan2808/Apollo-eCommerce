import 'package:apollodeliver/Models/Admin.dart';
import 'package:apollodeliver/Models/Product.dart';
import 'package:apollodeliver/Models/StoreCategory.dart';

class Store {
  int? id;
  String? name;
  String? logo;
  String? homeImage;
  String? dealsImage;
  String? dealsSquareImage;
  String? interactiveImage;
  Admin? admin;
  List<StoreCategory>? storeCategoryList;
  List<Product>? productList;

  Store({
    this.id,
    this.name,
    this.logo,
    this.homeImage,
    this.dealsImage,
    this.dealsSquareImage,
    this.interactiveImage,
    this.admin,
    this.storeCategoryList,
    this.productList,
  });

  factory Store.fromJson(Map<String, dynamic> json) {
    return Store(
      id: json['id'],
      name: json['name'],
      logo: json['logo'],
      homeImage: json['homeImage'],
      dealsImage: json['dealsImage'],
      dealsSquareImage: json['dealsSquareImage'],
      interactiveImage: json['interactiveImage'],
      admin: json['admin'] != null ? Admin.fromJson(json['admin']) : null,
      storeCategoryList: (json['storeCategoryList'] as List<dynamic>?)
          ?.map((item) => StoreCategory.fromJson(item))
          .toList(),
      productList: (json['productList'] as List<dynamic>?)
          ?.map((item) => Product.fromJson(item))
          .toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'logo': logo,
    'homeImage': homeImage,
    'dealsImage': dealsImage,
    'dealsSquareImage': dealsSquareImage,
    'interactiveImage': interactiveImage,
    'admin': admin?.toJson(),
    'storeCategoryList': storeCategoryList?.map((item) => item.toJson()).toList(),
    'productList': productList?.map((item) => item.toJson()).toList(),
  };
}
