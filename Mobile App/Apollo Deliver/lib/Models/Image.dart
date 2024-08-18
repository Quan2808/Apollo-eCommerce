import 'package:apollodeliver/Models/StoreCategory.dart';
import 'package:apollodeliver/Models/Variant.dart';

class Image {
  int? id;
  String? imgPath;
  Variant? variant;
  StoreCategory? storeCategory;

  Image({this.id, this.imgPath, this.variant, this.storeCategory});

  factory Image.fromJson(Map<String, dynamic> json) {
    return Image(
      id: json['id'],
      imgPath: json['imgPath'],
      variant: json['variant'] != null ? Variant.fromJson(json['variant']) : null,
      storeCategory: json['storeCategory'] != null ? StoreCategory.fromJson(json['storeCategory']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'imgPath': imgPath,
    'variant': variant?.toJson(),
    'storeCategory': storeCategory?.toJson(),
  };
}