import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/features/personalization/controllers/payment_method/payment_method_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class BillingPaymentSection extends StatelessWidget {
  const BillingPaymentSection({super.key});

  @override
  Widget build(BuildContext context) {
    final PaymentMethodController ctrl = Get.find();
    final theme = Theme.of(context).textTheme;

    return Column(
      children: [
        SectionHeading(
          title: 'Payment Method',
          buttonTitle: 'Change',
          onPressed: () => ctrl.setPaymentMethod(context),
        ),
        const SizedBox(height: TSizes.spaceBtwItems / 2),
        Obx(
          () => ctrl.selectedPaymentMethod.value.cardNumber != 0
              ? Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        const Icon(Iconsax.card, color: Colors.grey, size: 16),
                        const SizedBox(width: TSizes.spaceBtwItems / 2),
                        Expanded(
                          child: Text(
                            ctrl.selectedPaymentMethod.value.cardNumber
                                .toString(),
                            style: theme.bodyMedium,
                            softWrap: true,
                          ),
                        ),
                      ],
                    ),
                  ],
                )
              : Text('Select Payment Method', style: theme.bodyMedium),
        ),
      ],
    );
  }
}
