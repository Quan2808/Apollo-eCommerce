import 'package:apolloshop/common/widgets/stores/store_card.dart';
import 'package:apolloshop/common/widgets/custom_shapes/containers/rounded_container.dart';
import 'package:apolloshop/common/widgets/shimmers/store_shimmer.dart';
import 'package:apolloshop/data/models/store/store_model.dart';
import 'package:apolloshop/features/shop/controllers/store/store_controller.dart';
import 'package:apolloshop/features/shop/screens/store/widgets/store_products.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class StoreShowcase extends StatelessWidget {
  const StoreShowcase({
    super.key,
    required this.store,
  });

  final StoreModel store;

  @override
  Widget build(BuildContext context) {
    final storeController = Get.find<StoreController>();
    return Obx(() {
      if (storeController.isLoading.value) {
        return const StoreShimmer();
      }

      final productCount = storeController.productsCountByStore[store.id] ?? 0;
      final thumbnails = storeController.getProductThumbnailsByStore(store.id);

      return RoundedContainer(
        showBorder: true,
        borderColor: TColors.darkGrey,
        backgroundColor: Colors.transparent,
        padding: const EdgeInsets.all(TSizes.md),
        margin: const EdgeInsets.only(bottom: TSizes.spaceBtwItems),
        child: Column(
          children: [
            /// Store Card with Products Count
            StoreCard(
              store: store,
              showBorder: false,
              productCount: productCount,
              onTap: () => Get.to(
                () => StoreProducts(store: store),
              ),
            ),
            const SizedBox(height: TSizes.spaceBtwItems),

            /// Store Top 3 Products images
            if (thumbnails.isNotEmpty)
              Row(
                children: thumbnails
                    .map((thumbnail) =>
                        storeTopProductImageWidget(thumbnail, context))
                    .toList(),
              )
            else
              const Text('No products available'),
          ],
        ),
      );
    });
  }

  Widget storeTopProductImageWidget(String thumbnail, BuildContext context) {
    return Expanded(
      child: RoundedContainer(
        height: 100,
        backgroundColor: THelperFunctions.isDarkMode(context)
            ? TColors.darkerGrey
            : TColors.light,
        margin: const EdgeInsets.only(right: TSizes.sm),
        padding: const EdgeInsets.all(TSizes.md),
        child: Image.network(
          thumbnail,
          fit: BoxFit.cover,
          errorBuilder: (context, error, stackTrace) => const Icon(Icons.error),
        ),
      ),
    );
  }
}
