import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/data/models/payment_method/payment_method_model.dart';
import 'package:apolloshop/features/personalization/controllers/payment_method/payment_method_controller.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class PaymentTitle extends StatelessWidget {
  const PaymentTitle({
    super.key,
    required this.paymentMethod,
  });

  final PaymentMethodModel paymentMethod;

  @override
  Widget build(BuildContext context) {
    final controller = PaymentMethodController.instance;
    return ListTile(
      contentPadding: const EdgeInsets.all(0),
      onTap: () {
        controller.selectedPaymentMethod.value = paymentMethod;
        Get.back();
      },
      leading: RoundedContainer(
        width: 60,
        height: 40,
        backgroundColor: THelperFunctions.isDarkMode(context)
            ? TColors.light
            : TColors.white,
        padding: const EdgeInsets.all(TSizes.sm),
        // child: Image(
        //   image: AssetImage(paymentMethod.image),
        //   fit: BoxFit.contain,
        // ),
      ),
      title: Text(paymentMethod.type),
      trailing: const Icon(Iconsax.arrow_right_34),
    );
  }
}
