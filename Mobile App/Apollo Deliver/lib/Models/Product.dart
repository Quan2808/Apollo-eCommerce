import 'package:apollodeliver/Models/Bullet.dart';
import 'package:apollodeliver/Models/Category.dart';
import 'package:apollodeliver/Models/HashTag.dart';
import 'package:apollodeliver/Models/OptionTable.dart';
import 'package:apollodeliver/Models/Review.dart';
import 'package:apollodeliver/Models/Store.dart';

class Product {
  int? id;
  String? productName;
  String? description;
  Store? store;
  List<Bullet>? bullets;
  List<Category>? categories;
  List<OptionTable>? options;
  List<Review>? reviews;
  List<HashTag>? hashTags;

  Product({
    this.id,
    this.productName,
    this.description,
    this.store,
    this.bullets,
    this.categories,
    this.options,
    this.reviews,
    this.hashTags,
  });

  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
      id: json['id'],
      productName: json['productName'],
      description: json['description'],
      store: json['store'] != null ? Store.fromJson(json['store']) : null,
      bullets: (json['bullets'] as List?)?.map((item) => Bullet.fromJson(item)).toList(),
      categories: (json['categories'] as List?)?.map((item) => Category.fromJson(item)).toList(),
      options: (json['options'] as List?)?.map((item) => OptionTable.fromJson(item)).toList(),
      reviews: (json['reviews'] as List?)?.map((item) => Review.fromJson(item)).toList(),
      hashTags: (json['hashTags'] as List?)?.map((item) => HashTag.fromJson(item)).toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'productName': productName,
    'description': description,
    'store': store?.toJson(),
    'bullets': bullets?.map((item) => item.toJson()).toList(),
    'categories': categories?.map((item) => item.toJson()).toList(),
    'options': options?.map((item) => item.toJson()).toList(),
    'reviews': reviews?.map((item) => item.toJson()).toList(),
    'hashTags': hashTags?.map((item) => item.toJson()).toList(),
  };
}