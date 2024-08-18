import 'package:apolloshop/common/widgets/texts/stores/store_title_with_verified_icon.dart';
import 'package:apolloshop/common/widgets/texts/products/product_title_text.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/utils/constants/enums.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';

class ProductMetaData extends StatelessWidget {
  const ProductMetaData({super.key, required this.product});

  final ProductModel product;

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        /// Title
        ProductTitleText(title: product.title),
        const SizedBox(height: TSizes.spaceBtwItems / 2),

        /// Store
        Row(
          children: [
            StoreTitleWithVerifiedIcon(
              title: product.store != null ? product.store!.name : '',
              textSize: TextSizes.medium,
            ),
          ],
        )
      ],
    );
  }
}
