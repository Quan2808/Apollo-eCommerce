import 'dart:convert';

import 'package:apolloshop/data/models/user/user_model.dart';
import 'package:apolloshop/data/services/authentication/authentication_service.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';

class UserRepository extends GetxController {
  static UserRepository get instance => Get.find();

  final AuthenticationService _authService;
  final FlutterSecureStorage _secureStorage;

  UserRepository(this._authService, this._secureStorage);

  final Rx<UserModel?> _currentUser = Rx<UserModel?>(null);
  final RxString accessToken = ''.obs;

  UserModel? get currentUser => _currentUser.value;
  Rx<UserModel?> get currentUserStream => _currentUser;

  Future<void> initializeUser() async {
    final storedUser = await _secureStorage.read(key: 'user');
    if (storedUser != null) {
      _currentUser.value = UserModel.fromJson(jsonDecode(storedUser));
    }
    final storedToken = await _secureStorage.read(key: 'accessToken');
    if (storedToken != null) {
      accessToken.value = storedToken;
    }
  }

  Future<void> fetchUserInfo() async {
    try {
      final userInfo = await _authService.fetchUserInfo(accessToken.value);
      _currentUser.value = userInfo;
      await _secureStorage.write(
          key: 'user', value: jsonEncode(_currentUser.value!.toJson()));
    } catch (e) {
      rethrow;
    }
  }

  Future<void> signOut() async {
    _currentUser.value = null;
    accessToken.value = '';
    await _secureStorage.delete(key: 'user');
    await _secureStorage.delete(key: 'accessToken');
  }
}
