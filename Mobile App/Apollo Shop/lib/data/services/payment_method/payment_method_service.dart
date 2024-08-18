import 'dart:convert';
import 'package:apolloshop/data/models/payment_method/payment_method_model.dart';
import 'package:apolloshop/utils/constants/api_constants.dart';
import 'package:http/http.dart' as http;
import 'package:get/get.dart';

class PaymentMethodService extends GetxService {
  static PaymentMethodService get instance => Get.find();

  final String baseUrl = '${ApiConstants.baseApiUrl}/payments';

  Future<http.Response> getPaymentMethods(String userId) async {
    final url = Uri.parse('$baseUrl/payment-method/$userId');
    return await http.get(url);
  }

  Future<http.Response> createPaymentMethod(
      PaymentMethodModel paymentMethod) async {
    return await http.post(
      Uri.parse('$baseUrl/payment-method'),
      body: json.encode(paymentMethod.toJson()),
      headers: {
        'Content-Type': 'application/json',
      },
    );
  }

  Future<http.Response> updatePaymentMethod(
      PaymentMethodModel paymentMethod) async {
    final url = Uri.parse('$baseUrl/payment-method/${paymentMethod.id}');
    return await http.put(
      url,
      body: json.encode(paymentMethod.toJson()),
      headers: {
        'Content-Type': 'application/json',
      },
    );
  }
}
