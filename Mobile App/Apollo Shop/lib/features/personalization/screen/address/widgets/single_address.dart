import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/data/models/address/address_model.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';

class SingleAddress extends StatelessWidget {
  const SingleAddress({
    super.key,
    required this.address,
    required this.onTap,
  });

  final AddressModel address;
  final VoidCallback onTap;

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    return InkWell(
      child: RoundedContainer(
        width: double.infinity,
        showBorder: true,
        padding: const EdgeInsets.all(TSizes.md),
        backgroundColor: Colors.transparent,
        borderColor: dark ? TColors.darkerGrey : TColors.grey,
        margin: const EdgeInsets.only(bottom: TSizes.spaceBtwItems),
        child: Stack(
          children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  address.street,
                  softWrap: true,
                ),
                const SizedBox(height: TSizes.sm / 2),
              ],
            )
          ],
        ),
      ),
    );
  }
}
