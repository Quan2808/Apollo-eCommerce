import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:apolloshop/data/repositories/authentication/authentication_repository.dart';
import 'package:apolloshop/utils/helpers/network_manager.dart';
import 'package:apolloshop/features/authentication/screens/signup/verify_email.dart';
import 'package:apolloshop/utils/constants/image_strings.dart';
import 'package:apolloshop/utils/popups/full_screen_loader.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class SignupController extends GetxController {
  static SignupController get instance => Get.find();

  // Variables
  final hidePassword = true.obs;
  final privacyPolicy = false.obs;
  final fullName = TextEditingController();
  final email = TextEditingController();
  final phoneNumber = TextEditingController();
  final password = TextEditingController();
  GlobalKey<FormState> signupFormKey = GlobalKey<FormState>();

  void performSignUp() async {
    try {
      // Start Loading
      TFullScreenLoader.openLoadingDialog(
        'We are processing your information...',
        TImages.docerAnimation,
      );

      // Check Internet Connectivity
      final isConnected = await NetworkManager.instance.isConnected();
      if (!isConnected) {
        TFullScreenLoader.stopLoading();
        return;
      }

      // Form Validation
      if (!signupFormKey.currentState!.validate()) {
        TFullScreenLoader.stopLoading();
        return;
      }

      // Privacy Policy Check
      if (!privacyPolicy.value) {
        Loaders.warningSnackBar(
          title: 'Accept privacy policy',
          message:
              'In order to create account, you must have to read and accept the Privacy Policy & Terms of Use .',
        );
        TFullScreenLoader.stopLoading();
        return;
      }

      // Register & Save User
      await AuthenticationRepository.instance.signUp(
        fullName: fullName.text.trim(),
        email: email.text.trim(),
        phoneNumber: phoneNumber.text.trim(),
        password: password.text.trim(),
      );

      // Remove Loader
      TFullScreenLoader.stopLoading();

      // Show Success Message
      Loaders.successSnackBar(
        title: 'Congratulations',
        message: 'Account has been created.',
      );

      // Move to Verify Email Screen
      Get.to(() => VerifyEmailScreen(email: email.text.trim()));
    } catch (e) {
      // Remove Loader
      TFullScreenLoader.stopLoading();

      // Show some Generic Error to user
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
    }
  }
}
