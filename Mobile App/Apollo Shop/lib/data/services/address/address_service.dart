import 'dart:convert';

import 'package:apolloshop/data/models/address/address_model.dart';
import 'package:apolloshop/utils/constants/api_constants.dart';
import 'package:get/get.dart';
import 'package:http/http.dart' as http;

class AddressService extends GetxService {
  static AddressService get instance => Get.find();

  final String baseUrl = '${ApiConstants.baseApiUrl}/payments';

  Future<http.Response> getAddressList(String userId) async {
    final url = Uri.parse('$baseUrl/address/$userId');
    return await http.get(url);
  }

  Future<http.Response> createAddress(AddressModel address) async {
    return await http.post(
      Uri.parse('$baseUrl/address'),
      body: json.encode(address.toJson()),
      headers: {
        'Content-Type': 'application/json',
      },
    );
  }

  Future<http.Response> updateAddress(AddressModel address) async {
    final url = Uri.parse('$baseUrl/address');
    return await http.put(url, body: json.encode(address.toJson()), headers: {
      'Content-Type': 'application/json',
    });
  }
}
