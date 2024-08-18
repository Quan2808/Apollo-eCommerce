import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/features/shop/controllers/payment_shipping/shipping_method_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class BillingShippingSection extends StatelessWidget {
  const BillingShippingSection({super.key});

  @override
  Widget build(BuildContext context) {
    final ShippingMethodController ctrl = Get.find();
    final theme = Theme.of(context).textTheme;

    return Column(
      children: [
        SectionHeading(
          title: 'Shipping Method',
          buttonTitle: 'Change',
          onPressed: () => ctrl.setPaymentMethod(context),
        ),
        const SizedBox(height: TSizes.spaceBtwItems / 2),
        Obx(
          () => ctrl.selectedShippingMethod.value.id != 0
              ? Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        const Icon(Iconsax.card, color: Colors.grey, size: 16),
                        const SizedBox(width: TSizes.spaceBtwItems / 2),
                        Expanded(
                          child: Text(
                            ctrl.selectedShippingMethod.value.name.toString(),
                            style: theme.bodyMedium,
                            softWrap: true,
                          ),
                        ),
                      ],
                    ),
                  ],
                )
              : Text('Select Shipping Method', style: theme.bodyMedium),
        ),
      ],
    );
  }
}
