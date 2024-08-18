import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/features/personalization/controllers/address/address_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class BillingAddressSection extends StatelessWidget {
  const BillingAddressSection({super.key});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context).textTheme;
    final ctrl = AddressController.instance;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        SectionHeading(
          title: 'Shipping Address',
          buttonTitle: 'Change',
          onPressed: () => ctrl.setSelectedAddress(context),
        ),
        const SizedBox(height: TSizes.spaceBtwItems / 2),
        Obx(
          () => ctrl.selectedAddress.value.id.isNotEmpty
              ? Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        const Icon(Icons.location_on,
                            color: Colors.grey, size: 16),
                        const SizedBox(width: TSizes.spaceBtwItems / 2),
                        Expanded(
                          child: Text(
                            ctrl.selectedAddress.value.street,
                            style: theme.bodyMedium,
                            softWrap: true,
                          ),
                        ),
                      ],
                    ),
                  ],
                )
              : Text('Select address', style: theme.bodyMedium),
        ),
      ],
    );
  }
}
