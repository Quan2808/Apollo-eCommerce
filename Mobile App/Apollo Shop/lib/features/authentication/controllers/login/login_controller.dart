import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:apolloshop/data/repositories/authentication/authentication_repository.dart';
import 'package:apolloshop/utils/helpers/network_manager.dart';
import 'package:apolloshop/utils/constants/image_strings.dart';
import 'package:apolloshop/utils/popups/full_screen_loader.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';

class LoginController extends GetxController {
  // static LoginController get instance => Get.find();

  // Variables
  final localStorage = GetStorage();
  final hidePassword = true.obs;
  final email = TextEditingController();
  final password = TextEditingController();
  GlobalKey<FormState> logInFormKey = GlobalKey<FormState>();

  Future<void> performSignIn() async {
    try {
      // Start Loading
      TFullScreenLoader.openLoadingDialog(
        'Logging you in...',
        TImages.docerAnimation,
      );

      // Check Internet Connectivity
      final isConnected = await NetworkManager.instance.isConnected();
      if (!isConnected) {
        TFullScreenLoader.stopLoading();
        return;
      }

      // Form Validation
      if (!logInFormKey.currentState!.validate()) {
        TFullScreenLoader.stopLoading();
        return;
      }

      // Sign In User using Email & Password Authentication
      await AuthenticationRepository.instance.signIn(
        email: email.text.trim(),
        password: password.text.trim(),
      );

      // Remove Loader
      TFullScreenLoader.stopLoading();

      // Redirect
      AuthenticationRepository.instance.screenRedirect();
    } catch (e) {
      // Remove Loader
      TFullScreenLoader.stopLoading();

      // Show some Generic Error to user
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
    }
  }
}
