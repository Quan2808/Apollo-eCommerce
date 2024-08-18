import 'package:apolloshop/data/models/user/user_model.dart';
import 'package:apolloshop/data/repositories/user/user_repository.dart';
import 'package:apolloshop/data/services/authentication/authentication_service.dart';
import 'package:apolloshop/utils/constants/api_constants.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:get/get.dart';

class UserController extends GetxController {
  static UserController instance = Get.find();

  final AuthenticationService _authService =
      AuthenticationService(ApiConstants.baseApiUrl);
  final _secureStorage = const FlutterSecureStorage();

  late final UserRepository _userRepository;
  final Rx<UserModel?> user = UserModel.empty().obs;
  final profileLoading = false.obs;

  @override
  void onInit() {
    super.onInit();
    _userRepository = UserRepository(_authService, _secureStorage);
    ever(_userRepository.currentUserStream, (_) => update());
    initializeUser();
  }

  Future<void> initializeUser() async {
    await _userRepository.initializeUser();
    await fetchUserInfo();
  }

  Future<void> fetchUserInfo() async {
    try {
      profileLoading.value = true;
      await _userRepository.fetchUserInfo();
      user.value = _userRepository.currentUser;
    } catch (e) {
      user.value = UserModel.empty();
    } finally {
      profileLoading.value = false;
    }
  }

  // String get fullName => user.value?.fullName ?? 'Guest';
}
