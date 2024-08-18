import 'package:apolloshop/common/widgets/stores/store_show_case.dart';
import 'package:apolloshop/common/widgets/layouts/grid_layout.dart';
import 'package:apolloshop/common/widgets/products/product_cards/product_card_vertical.dart';
import 'package:apolloshop/common/widgets/texts/section_heading.dart';
import 'package:apolloshop/data/models/category/category_model.dart';
import 'package:apolloshop/features/shop/controllers/product/product_controller.dart';
import 'package:apolloshop/features/shop/controllers/store/store_controller.dart';
import 'package:apolloshop/features/shop/screens/sub_category/sub_categories.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class CategoryTab extends StatelessWidget {
  const CategoryTab({
    super.key,
    required this.category,
  });

  final CategoryModel category;

  @override
  Widget build(BuildContext context) {
    final storesForCategory =
        StoreController.instance.getStoresByCategory(category.id);
    ProductController.instance.filterByCategory(category);
    final products = ProductController.instance.filteredProducts;

    return ListView(
      shrinkWrap: true,
      physics: const AlwaysScrollableScrollPhysics(),
      children: [
        Padding(
          padding: const EdgeInsets.all(TSizes.defaultSpace),
          child: Column(
            children: [
              ...storesForCategory.map((store) => StoreShowcase(
                    store: store,
                  )),
              const SizedBox(height: TSizes.spaceBtwSections),
              SectionHeading(
                title: 'You might like',
                // showActionButton: false,
                onPressed: () =>
                    Get.to(() => SubCategoriesScreen(category: category)),
              ),
              const SizedBox(height: TSizes.spaceBtwItems),
              GridLayout(
                mainAxisExtent: 271,
                itemCount: products.length,
                itemBuilder: (_, index) => ProductCardVertical(
                  product: products[index],
                ),
              ),
              const SizedBox(height: TSizes.spaceBtwSections),
            ],
          ),
        ),
      ],
    );
  }
}
