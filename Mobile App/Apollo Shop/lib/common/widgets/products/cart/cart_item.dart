import 'package:apolloshop/common/widgets/images/rounded_image.dart';
import 'package:apolloshop/common/widgets/texts/products/product_attribute_text.dart';
import 'package:apolloshop/common/widgets/texts/products/product_title_text.dart';
import 'package:apolloshop/data/models/cart/cart_item_model.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';

class CartItem extends StatelessWidget {
  const CartItem({
    super.key,
    required this.cartItem,
  });

  final CartItemModel cartItem;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        /// Image
        RoundedImage(
          isNetworkImage: true,
          imageUrl: cartItem.variant!.img,
          width: 60,
          height: 60,
          padding: const EdgeInsets.all(TSizes.sm),
          backgroundColor: THelperFunctions.isDarkMode(context)
              ? TColors.darkerGrey
              : TColors.light,
        ),
        const SizedBox(width: TSizes.spaceBtwItems),

        /// Title, Attribute & Price
        Expanded(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // StoreTitleWithVerifiedIcon(title: 'All Store'),
              Flexible(
                child: ProductTitleText(
                    title: cartItem.variant!.name, maxLines: 1),
              ),
              Flexible(
                child: ProductAttributeText(title: cartItem.variant!.name),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
