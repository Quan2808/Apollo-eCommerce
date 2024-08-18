import 'dart:convert';
import 'package:apolloshop/data/models/payment_method/payment_method_model.dart';
import 'package:apolloshop/data/services/payment_method/payment_method_service.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:get/get.dart';

class PaymentMethodRepository extends GetxController {
  static PaymentMethodRepository get instance => Get.find();

  final PaymentMethodService _paymentMethodService =
      PaymentMethodService.instance;

  Future<List<PaymentMethodModel>> fetchPaymentMethods() async {
    try {
      final userId = UserController.instance.user.value!.id;
      final response = await _paymentMethodService.getPaymentMethods(userId);
      List<dynamic> jsonResponse = json.decode(response.body);
      return jsonResponse
          .map((paymentMethodJson) =>
              PaymentMethodModel.fromJson(paymentMethodJson))
          .toList();
    } catch (e) {
      throw 'Something went wrong while fetching payment methods. Try again later.';
    }
  }

  Future<PaymentMethodModel> addNewPaymentMethod(
      PaymentMethodModel paymentMethod) async {
    try {
      final response =
          await _paymentMethodService.createPaymentMethod(paymentMethod);
      return PaymentMethodModel.fromJson(json.decode(response.body));
    } catch (e) {
      throw 'Unable to add new payment method. Try again later.';
    }
  }

  Future<PaymentMethodModel> updatePaymentMethod(
      PaymentMethodModel paymentMethod) async {
    try {
      final response =
          await _paymentMethodService.updatePaymentMethod(paymentMethod);
      return PaymentMethodModel.fromJson(json.decode(response.body));
    } catch (e) {
      throw 'Unable to update payment method. Try again later.';
    }
  }
}
