import 'package:apollodeliver/Models/OptionTable.dart';
import 'package:apollodeliver/Models/Variant.dart';

class OptionValue {
  int? id;
  String? value;
  OptionTable? optionTable;
  List<Variant>? variants;

  OptionValue({this.id, this.value, this.optionTable, this.variants});

  factory OptionValue.fromJson(Map<String, dynamic> json) {
    return OptionValue(
      id: json['id'],
      value: json['value'],
      optionTable: json['optionTable'] != null ? OptionTable.fromJson(json['optionTable']) : null,
      variants: (json['variants'] as List?)?.map((item) => Variant.fromJson(item)).toList(),
    );
  }

  Map<String, dynamic> toJson() => {
    'id': id,
    'value': value,
    'optionTable': optionTable?.toJson(),
    'variants': variants?.map((item) => item.toJson()).toList(),
  };
}
