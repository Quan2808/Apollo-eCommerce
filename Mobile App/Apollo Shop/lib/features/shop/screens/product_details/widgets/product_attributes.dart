import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/common/widgets/images/rounded_image.dart';
import 'package:apolloshop/common/widgets/texts/products/product_price_text.dart';
import 'package:apolloshop/common/widgets/texts/products/product_title_text.dart';
import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/data/models/product/variant_model.dart';
import 'package:apolloshop/features/shop/controllers/product/variant_controller.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class ProductAttributes extends StatelessWidget {
  const ProductAttributes({
    super.key,
    required this.product,
  });

  final ProductModel product;

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);

    // Initialize VariantController with the productId
    final variantController = Get.put(VariantController());
    variantController.fetchVariantsByProduct(product.id);

    return Obx(() {
      if (variantController.isLoading.value) {
        return const Center(child: CircularProgressIndicator());
      }

      // Check if there are any variants available
      final hasVariants = variantController.variants.isNotEmpty;

      // Get the selected variant or use an empty variant if none are available
      final selectedVariant = hasVariants
          ? variantController.selectedVariant.value ??
              variantController.variants.first
          : VariantModel.empty();

      return Column(
        children: [
          /// Select Attributes Pricing & Description
          RoundedContainer(
            padding: const EdgeInsets.all(TSizes.md),
            backgroundColor: dark ? TColors.darkerGrey : TColors.grey,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                /// Title & Price
                Row(
                  children: [
                    const SectionHeading(
                        title: 'Variation', showActionButton: false),
                    const SizedBox(width: TSizes.spaceBtwItems),
                    const ProductTitleText(title: 'Price: ', smallSize: true),
                    ProductPriceText(
                        price: hasVariants
                            ? selectedVariant.price.toString()
                            : 'N/A'),
                  ],
                ),
                const SizedBox(height: TSizes.spaceBtwItems),

                /// Variant Name
                ProductTitleText(
                  title: hasVariants
                      ? selectedVariant.name
                      : 'No description available.',
                  maxLines: 4,
                  smallSize: true,
                ),
                const SizedBox(height: TSizes.spaceBtwItems),

                /// SKU Code
                if (hasVariants)
                  ProductTitleText(
                    title: 'SKU: ${selectedVariant.skuCode}',
                    smallSize: true,
                  ),
                const SizedBox(height: TSizes.spaceBtwItems),

                /// Weight
                if (hasVariants)
                  ProductTitleText(
                    title: 'Weight: ${selectedVariant.weight} kg',
                    smallSize: true,
                  ),
                const SizedBox(height: TSizes.spaceBtwItems),
              ],
            ),
          ),
          const SizedBox(height: TSizes.spaceBtwItems),

          /// Attributes
          SizedBox(
            height: 80,
            child: ListView.separated(
                itemCount: hasVariants ? variantController.variants.length : 0,
                shrinkWrap: true,
                scrollDirection: Axis.horizontal,
                physics: const AlwaysScrollableScrollPhysics(),
                separatorBuilder: (_, __) => const SizedBox(
                      width: TSizes.spaceBtwItems,
                    ),
                itemBuilder: (_, index) => Obx(
                      () {
                        if (!hasVariants) {
                          return const SizedBox
                              .shrink(); // Return empty widget if no variants
                        }

                        final variant = variantController.variants[index];
                        final isSelected =
                            variantController.selectedVariant.value == variant;

                        return RoundedImage(
                          width: 80,
                          backgroundColor: dark ? TColors.black : TColors.white,
                          border: Border.all(
                              color:
                                  isSelected ? TColors.primary : TColors.grey),
                          padding: const EdgeInsets.all(TSizes.sm),
                          isNetworkImage: true,
                          imageUrl: variant.img,
                          onPressed: () =>
                              variantController.selectedVariant.value = variant,
                        );
                      },
                    )),
          ),
        ],
      );
    });
  }
}
