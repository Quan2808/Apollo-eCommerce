import 'dart:convert';

import 'package:apolloshop/data/models/shipping_method/shipping_method_model.dart';
import 'package:apolloshop/utils/constants/api_constants.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;

class ShippingMethodService extends GetxService {
  static ShippingMethodService get instance => Get.find();

  final String baseUrl = '${ApiConstants.baseApiUrl}/payments';

  Future<http.Response> getShippingMethods() async {
    final url = Uri.parse('$baseUrl/shipping-method');
    return await http.get(url);
  }

  Future<http.Response> createPaymentMethod(
      ShippingMethodModel shippingMethod) async {
    return await http.post(
      Uri.parse('$baseUrl/payment-method'),
      body: json.encode(shippingMethod.toJson()),
      headers: {
        'Content-Type': 'application/json',
      },
    );
  }
}
