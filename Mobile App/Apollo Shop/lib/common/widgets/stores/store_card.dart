import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/common/widgets/texts/stores/store_title_with_verified_icon.dart';
import 'package:apolloshop/data/models/store/store_model.dart';
import 'package:apolloshop/utils/constants/enums.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class StoreCard extends StatelessWidget {
  const StoreCard({
    super.key,
    required this.showBorder,
    this.onTap,
    required this.store,
    required this.productCount, // Add productCount parameter
  });

  final StoreModel store;
  final bool showBorder;
  final void Function()? onTap;
  final int productCount; // Add productCount field

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: RoundedContainer(
        padding: const EdgeInsets.all(TSizes.sm),
        showBorder: showBorder,
        backgroundColor: Colors.transparent,
        child: Row(
          children: [
            /// Text
            Expanded(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  StoreTitleWithVerifiedIcon(
                      title: store.name, textSize: TextSizes.large),
                  Text(
                    /// Product quantity
                    '$productCount products',
                    overflow: TextOverflow.ellipsis,
                    style: Theme.of(context).textTheme.labelMedium,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
