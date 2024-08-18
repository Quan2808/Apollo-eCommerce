import 'package:apollodeliver/Models/OptionValue.dart';
import 'package:apollodeliver/Models/Product.dart';

class OptionTable {
  int? id;
  String? name;
  List<OptionValue>? optionValues;
  Product? product;

  OptionTable({this.id, this.name, this.optionValues, this.product});

  factory OptionTable.fromJson(Map<String, dynamic> json) {
    return OptionTable(
      id: json['id'],
      name: json['name'],
      optionValues: (json['optionValues'] as List?)?.map((item) => OptionValue.fromJson(item)).toList(),
      product: json['product'] != null ? Product.fromJson(json['product']) : null,
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'optionValues': optionValues?.map((item) => item.toJson()).toList(),
    'product': product?.toJson(),
  };
}