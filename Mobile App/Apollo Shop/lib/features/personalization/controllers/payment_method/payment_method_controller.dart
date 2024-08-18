import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/data/models/payment_method/payment_method_model.dart';
import 'package:apolloshop/data/repositories/payment_method/payment_method_repository.dart';
import 'package:apolloshop/data/services/payment_method/payment_method_service.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:apolloshop/features/personalization/screen/payment_method/widgets/add_new_payment_method.dart';
import 'package:apolloshop/utils/constants/image_strings.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:apolloshop/utils/helpers/network_manager.dart';
import 'package:apolloshop/utils/popups/full_screen_loader.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class PaymentMethodController extends GetxController {
  static PaymentMethodController get instance => Get.find();

  final RxList<PaymentMethodModel> paymentMethods = <PaymentMethodModel>[].obs;
  final Rx<PaymentMethodModel> selectedPaymentMethod =
      PaymentMethodModel.empty().obs;

  final PaymentMethodRepository paymentMethodRepository =
      Get.put(PaymentMethodRepository());

  RxBool refreshData = true.obs;
  final cardNumberController = TextEditingController();
  final nameOnCardController = TextEditingController();
  final expirationDateController = TextEditingController();
  final isDefaultPayment = false.obs;

  final _paymentMethodService = PaymentMethodService.instance;

  @override
  void onClose() {
    cardNumberController.dispose();
    nameOnCardController.dispose();
    expirationDateController.dispose();
    super.onClose();
  }

  Future<List<PaymentMethodModel>> getPaymentMethods() async {
    try {
      final response = await paymentMethodRepository.fetchPaymentMethods();
      selectedPaymentMethod.value = response.firstWhere(
        (e) => e.selectedPM,
        orElse: () => PaymentMethodModel.empty(),
      );
      return response;
    } catch (e) {
      Get.snackbar('Error', 'Unable to fetch payment methods');
      return [];
    }
  }

  Future<void> addNewPaymentMethod() async {
    try {
      TFullScreenLoader.openLoadingDialog(
        'Storing Payment Method',
        TImages.docerAnimation,
      );

      final isConnected = await NetworkManager.instance.isConnected();
      if (!isConnected) {
        TFullScreenLoader.stopLoading();
        return;
      }

      if (cardNumberController.text.isEmpty ||
          nameOnCardController.text.isEmpty ||
          expirationDateController.text.isEmpty) {
        Loaders.errorSnackBar(
            title: 'Error', message: 'Please fill in all fields');
        return;
      }

      final paymentMethod = PaymentMethodModel(
        id: 0,
        user: UserController.instance.user.value!,
        cardNumber: int.parse(cardNumberController.text),
        nameOnCard: nameOnCardController.text,
        expirationDate: expirationDateController.text,
        defaultPayment: isDefaultPayment.value,
        type: _determineCardType(int.parse(cardNumberController.text)),
      );

      await _paymentMethodService.createPaymentMethod(paymentMethod);
      TFullScreenLoader.stopLoading();

      Loaders.successSnackBar(
          title: 'Address Added',
          message: 'Your address has been saved successfully');

      refreshData.toggle();

      final getPaymentMethod =
          await paymentMethodRepository.fetchPaymentMethods();
      selectedPaymentMethod.value = getPaymentMethod.firstWhere(
        (e) => e.selectedPM,
        orElse: () => PaymentMethodModel.empty(),
      );
      cardNumberController.clear();
      nameOnCardController.clear();
      expirationDateController.clear();
      Navigator.of(Get.context!).pop();
    } catch (e) {
      Get.snackbar('Error', 'Unable to add new payment method');
      Navigator.of(Get.context!).pop();
    }
  }

  String _determineCardType(int cardNumber) {
    if (cardNumber.toString().startsWith('4')) {
      return 'Visa';
    } else if (cardNumber.toString().startsWith('5')) {
      return 'MasterCard';
    } else {
      return 'Unknown';
    }
  }

  Future<void> setPaymentMethod(BuildContext context) async {
    await showModalBottomSheet(
      context: context,
      builder: (_) => SingleChildScrollView(
        child: Container(
          padding: const EdgeInsets.all(TSizes.lg),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SectionHeading(
                title: 'Select Payment Method',
                showActionButton: false,
              ),
              const SizedBox(height: TSizes.spaceBtwItems),
              FutureBuilder<List<PaymentMethodModel>>(
                future: getPaymentMethods(),
                builder: (_, snapshot) {
                  final response = THelperFunctions.checkMultiRecordState(
                      snapshot: snapshot);
                  if (response != null) {
                    return response;
                  } else if (snapshot.hasData && snapshot.data!.isNotEmpty) {
                    return Column(
                      children: snapshot.data!.map((paymentMethod) {
                        return ListTile(
                          title: Text(paymentMethod.cardNumber.toString()),
                          subtitle: Text(paymentMethod.nameOnCard!),
                          onTap: () async {
                            selectedPaymentMethod.value = paymentMethod;
                            Navigator.of(context).pop();
                          },
                        );
                      }).toList(),
                    );
                  } else {
                    return const Center(
                        child: Text('No payment methods available.'));
                  }
                },
              ),
              const SizedBox(height: TSizes.spaceBtwItems / 2),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  child: const Text('Add New Payment Method'),
                  onPressed: () =>
                      Get.to(() => const AddNewPaymentMethodScreen()),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
