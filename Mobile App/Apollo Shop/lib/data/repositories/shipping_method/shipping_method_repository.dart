import 'dart:convert';

import 'package:apolloshop/data/models/shipping_method/shipping_method_model.dart';
import 'package:apolloshop/data/services/shipping_method/shipping_method_service.dart';
import 'package:get/get.dart';

class ShippingMethodRepository extends GetxService {
  static ShippingMethodRepository get instance => Get.find();

  final ShippingMethodService _shippingMethodService =
      ShippingMethodService.instance;

  Future<List<ShippingMethodModel>> fetchShippingMethods() async {
    try {
      final response = await _shippingMethodService.getShippingMethods();
      List<dynamic> jsonResponse = json.decode(response.body);
      return jsonResponse
          .map((shippingMethodJson) =>
              ShippingMethodModel.fromJson(shippingMethodJson))
          .toList();
    } catch (e) {
      throw 'Something went wrong while fetching payment methods. Try again later.';
    }
  }
}
