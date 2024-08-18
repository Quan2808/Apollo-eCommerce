import 'package:apolloshop/common/widgets/layouts/grid_layout.dart';
import 'package:apolloshop/common/widgets/products/product_cards/product_card_vertical.dart';
import 'package:apolloshop/data/models/category/category_model.dart';
import 'package:apolloshop/data/models/store/store_model.dart';
import 'package:apolloshop/features/shop/controllers/product/product_controller.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:iconsax/iconsax.dart';

class SortableProducts extends StatelessWidget {
  const SortableProducts({
    super.key,
    this.store,
    this.category,
  });

  final StoreModel? store;
  final CategoryModel? category;

  @override
  Widget build(BuildContext context) {
    final productController = ProductController.instance;

    // Filter products based on store and category
    if (store != null) {
      productController.filterByStore(store!);
    }

    if (category != null) {
      productController.filterByCategory(category!);
    }

    return Obx(() {
      return Column(
        children: [
          // Dropdown for sort
          DropdownButtonFormField<String>(
            decoration: const InputDecoration(
              prefixIcon: Icon(Iconsax.sort),
            ),
            hint: const Text('Sort by'),
            onChanged: (value) {
              if (value != null) {
                productController.sortProducts(value);
              }
            },
            items: [
              'Name Ascending',
              'Name Descending',
            ]
                .map((option) => DropdownMenuItem(
                      value: option,
                      child: Text(option),
                    ))
                .toList(),
          ),
          const SizedBox(height: TSizes.spaceBtwSections),

          // Conditionally display the category filter dropdown
          if (category == null) ...[
            DropdownButtonFormField<CategoryModel>(
              isExpanded: true,
              decoration: const InputDecoration(
                prefixIcon: Icon(Iconsax.category),
              ),
              hint: const Text('Select Category'),
              onChanged: (CategoryModel? category) {
                if (category != null) {
                  productController.filterByCategory(category);
                }
              },
              items: [
                const DropdownMenuItem<CategoryModel>(
                  value: null,
                  child: Text('All Categories'),
                ),
                ...productController.filteredCategories.map(
                  (category) => DropdownMenuItem(
                    value: category,
                    child: Text(category.attribute),
                  ),
                ),
              ],
            ),
            const SizedBox(height: TSizes.spaceBtwSections),
          ],

          // Conditionally display the store filter dropdown
          if (store == null) ...[
            DropdownButtonFormField<StoreModel>(
              isExpanded: true,
              decoration: const InputDecoration(
                prefixIcon: Icon(Iconsax.category),
              ),
              hint: const Text('Select Store'),
              onChanged: (StoreModel? store) {
                if (store != null) {
                  productController.filterByStore(store);
                }
              },
              items: [
                const DropdownMenuItem<StoreModel>(
                  value: null,
                  child: Text('All Stores'),
                ),
                ...productController.filteredStores.map(
                  (store) => DropdownMenuItem(
                    value: store,
                    child: Text(store.name),
                  ),
                ),
              ],
            ),
            const SizedBox(height: TSizes.spaceBtwSections),
          ],

          // Search Field
          Padding(
            padding: const EdgeInsets.all(TSizes.sm),
            child: TextField(
              onChanged: (value) {
                productController.filterByName(value);
              },
              decoration: InputDecoration(
                prefixIcon: const Icon(Icons.search),
                hintText: 'Search by name',
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(10),
                ),
              ),
            ),
          ),
          const SizedBox(height: TSizes.spaceBtwSections),

          // Products
          GridLayout(
            mainAxisExtent: 271,
            itemCount: productController.filteredProducts.isEmpty
                ? productController.products.length
                : productController.filteredProducts.length,
            itemBuilder: (_, index) => ProductCardVertical(
              product: productController.filteredProducts.isEmpty
                  ? productController.products[index]
                  : productController.filteredProducts[index],
            ),
          ),
        ],
      );
    });
  }
}
