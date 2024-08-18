import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:get/get.dart';

class VerifyEmailController extends GetxController {
  static VerifyEmailController get instance => Get.find();

  // Send Email whenever Verify Screen appears
  @override
  void onInit() {
    sendEmailVerification();
    super.onInit();
  }

  // Send Email Verification
  sendEmailVerification() async {
    try {
      Loaders.successSnackBar(
          title: 'Email Sent!',
          message: 'Please Check your inbox and verify your email.');
    } catch (e) {
      Loaders.errorSnackBar(title: 'On Snap!', message: e.toString());
    }
  }
}
