import 'dart:convert';

import 'package:apolloshop/data/models/user/user_model.dart';
import 'package:http/http.dart' as http;

class AuthenticationService {
  final String _baseUrl;

  AuthenticationService(this._baseUrl);

  Future<Map<String, dynamic>> signIn({
    required String email,
    required String password,
  }) async {
    final response = await http.post(
      Uri.parse('$_baseUrl/login'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode({
        'email': email,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      final Map<String, dynamic> data = jsonDecode(response.body);
      return {
        'accessToken': data['accessToken'],
      };
    } else {
      final Map<String, dynamic> errorData = jsonDecode(response.body);
      final errorMessage = errorData['message'] ?? 'An error occurred';
      throw Exception('Failed to sign in: $errorMessage');
    }
  }

  Future<UserModel> fetchUserInfo(String token) async {
    final response = await http.get(
      Uri.parse('$_baseUrl/customer/info'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $token',
      },
    );

    if (response.statusCode == 200) {
      final Map<String, dynamic> data = jsonDecode(response.body);
      return UserModel.fromJson(data);
    } else {
      // throw Exception('Failed to fetch user info');
      final Map<String, dynamic> errorData = jsonDecode(response.body);
      final errorMessage = errorData['message'] ?? 'An error occurred';
      throw Exception('Failed to sign in: $errorMessage');
    }
  }

  Future<void> signUp({
    required String clientName,
    required String email,
    required String phoneNumber,
    required String password,
  }) async {
    final response = await http.post(
      Uri.parse('$_baseUrl/register'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode({
        'clientName': clientName,
        'email': email,
        'phoneNumber': phoneNumber,
        'password': password,
      }),
    );

    if (response.statusCode == 200) {
      jsonDecode(response.body);
    } else {
      // Handle errors
      jsonDecode(response.body);
      throw Exception(
          'Failed to register. Status code: ${response.statusCode}');
    }
  }
}
