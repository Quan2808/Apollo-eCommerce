import 'package:apollodeliver/Models/CartLine.dart';
import 'package:apollodeliver/Models/Image.dart';
import 'package:apollodeliver/Models/OptionValue.dart';
import 'package:apollodeliver/Models/Product.dart';
import 'package:apollodeliver/Models/Review.dart';
import 'package:apollodeliver/Models/SaveForLater.dart';
import 'package:apollodeliver/Models/ShopOrder.dart';
import 'package:apollodeliver/Models/Video.dart';

class Variant {
  int? id;
  String? name;
  String? skuCode;
  int? stockQuantity;
  double? weight;
  double? price;
  double? salePrice;
  String? img;
  bool? isDeleted;
  Product? product;
  List<Image>? images;
  List<Video>? videos;
  List<OptionValue>? optionValues;
  CartLine? cartLine;
  SaveForLater? saveForLater;
  List<Review>? reviews;
  Set<ShopOrder>? shopOrders;

  Variant({
    this.id,
    this.name,
    this.skuCode,
    this.stockQuantity,
    this.weight,
    this.price,
    this.salePrice,
    this.img,
    this.isDeleted,
    this.product,
    this.images,
    this.videos,
    this.optionValues,
    this.cartLine,
    this.saveForLater,
    this.reviews,
    this.shopOrders,
  });

  factory Variant.fromJson(Map<String, dynamic> json) {
    return Variant(
      id: json['id'],
      name: json['name'],
      skuCode: json['skuCode'],
      stockQuantity: json['stockQuantity'],
      weight: (json['weight'] as num?)?.toDouble(),
      price: (json['price'] as num?)?.toDouble(),
      salePrice: (json['salePrice'] as num?)?.toDouble(),
      img: json['img'],
      isDeleted: json['isDeleted'],
      product: json['product'] != null ? Product.fromJson(json['product']) : null,
      images: (json['images'] as List?)?.map((item) => Image.fromJson(item)).toList(),
      videos: (json['videos'] as List?)?.map((item) => Video.fromJson(item)).toList(),
      optionValues: (json['optionValues'] as List?)?.map((item) => OptionValue.fromJson(item)).toList(),
      cartLine: json['cartLine'] != null ? CartLine.fromJson(json['cartLine']) : null,
      saveForLater: json['saveForLater'] != null ? SaveForLater.fromJson(json['saveForLater']) : null,
      reviews: (json['reviews'] as List?)?.map((item) => Review.fromJson(item)).toList(),
      shopOrders: (json['shopOrders'] as List?)?.map((item) => ShopOrder.fromJson(item)).toSet(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'skuCode': skuCode,
    'stockQuantity': stockQuantity,
    'weight': weight,
    'price': price,
    'salePrice': salePrice,
    'img': img,
    'isDeleted': isDeleted,
    'product': product?.toJson(),
    'images': images?.map((item) => item.toJson()).toList(),
    'videos': videos?.map((item) => item.toJson()).toList(),
    'optionValues': optionValues?.map((item) => item.toJson()).toList(),
    'cartLine': cartLine?.toJson(),
    'saveForLater': saveForLater?.toJson(),
    'reviews': reviews?.map((item) => item.toJson()).toList(),
    'shopOrders': shopOrders?.map((item) => item.toJson()).toList(),
  };
}