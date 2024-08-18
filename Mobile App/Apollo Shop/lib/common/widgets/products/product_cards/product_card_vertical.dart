import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/common/widgets/images/rounded_image.dart';
import 'package:apolloshop/common/widgets/products/product_cards/product_card_add_to_cart_button.dart';
import 'package:apolloshop/common/widgets/texts/products/product_title_text.dart';
import 'package:apolloshop/common/widgets/texts/stores/store_title_with_verified_icon.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/features/shop/controllers/product/variant_controller.dart';
import 'package:apolloshop/features/shop/screens/product_details/product_detail.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ProductCardVertical extends StatelessWidget {
  const ProductCardVertical({
    super.key,
    required this.product,
  });

  final ProductModel product;

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    final variantController = Get.find<VariantController>();

    if (variantController.variants.isEmpty) {
      variantController.fetchVariantsByProduct(product.id);
    }

    return GestureDetector(
      onTap: () {
        Get.to(() => ProductDetailScreen(product: product));
      },
      child: Container(
        width: 180,
        padding: const EdgeInsets.all(1),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(TSizes.productImageRadius),
          color: dark ? TColors.darkerGrey : TColors.lightContainer,
        ),
        child: Column(
          children: [
            // Thumbnail
            RoundedContainer(
              height: 180,
              padding: const EdgeInsets.all(TSizes.sm),
              backgroundColor: dark ? TColors.dark : TColors.light,
              child: Stack(
                children: [
                  RoundedImage(
                    imageUrl: product.thumbnail,
                    applyImageRadius: true,
                    isNetworkImage: true,
                  ),
                ],
              ),
            ),
            const SizedBox(height: TSizes.spaceBtwItems / 2),

            // Title
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: TSizes.sm),
              child: SizedBox(
                width: double.infinity,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    ProductTitleText(title: product.title, smallSize: true),
                  ],
                ),
              ),
            ),
            const SizedBox(height: TSizes.spaceBtwItems / 2),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                // Store
                Padding(
                  padding: const EdgeInsets.only(left: TSizes.sm * 2),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      StoreTitleWithVerifiedIcon(title: product.store!.name),
                    ],
                  ),
                ),
                const Spacer(),

                // Add to cart
                ProductCardAddToCartButton(product: product),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

