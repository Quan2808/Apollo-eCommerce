import 'dart:convert';

import 'package:apolloshop/data/models/address/address_model.dart';
import 'package:apolloshop/data/services/address/address_service.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:get/get.dart';

class AddressRepository extends GetxController {
  static AddressRepository get instance => Get.find();

  final _addressService = AddressService.instance;

  Future<List<AddressModel>> fetchUserAddresses() async {
    try {
      final userId = UserController.instance.user.value!.id;
      final response = await _addressService.getAddressList(userId);

      List<dynamic> jsonResponse = json.decode(response.body);

      return jsonResponse
          .map((addressJson) => AddressModel.fromSnapshot(addressJson))
          .toList();
    } catch (e) {
      throw 'Something went wrong while fetching addresses. Try again later.';
    }
  }

  // Add new address
  Future<Map<String, dynamic>> addNewAddress(AddressModel address) async {
    try {
      final response = await _addressService.createAddress(address);
      return json.decode(response.body);
    } catch (e) {
      throw 'Unable to add new address. Try again later.';
    }
  }
}
