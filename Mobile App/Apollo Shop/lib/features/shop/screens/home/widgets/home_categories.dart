import 'package:apolloshop/common/widgets/image_text/vertical_image_text.dart';
import 'package:apolloshop/common/widgets/shimmers/category_shimmer.dart';
import 'package:apolloshop/features/shop/controllers/category/category_controller.dart';
import 'package:apolloshop/features/shop/screens/sub_category/sub_categories.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class HomeCategories extends StatelessWidget {
  const HomeCategories({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    final categoryController = Get.put(CategoryController());
    return Obx(() {
      if (categoryController.isLoading.value) {
        return const CategoryShimmer();
      }
      return Obx(() {
        if (categoryController.isLoading.value) {
          return const CategoryShimmer();
        }
        if (categoryController.categories.isEmpty) {
          return Center(
            child: Text(
              'No Data Found!',
              style: Theme.of(context)
                  .textTheme
                  .bodyMedium!
                  .apply(color: Colors.white),
            ),
          );
        }
        return SizedBox(
          height: 80,
          child: ListView.builder(
            shrinkWrap: true,
            itemCount: categoryController.categories.length > 8
                ? 8
                : categoryController.categories.length,
            scrollDirection: Axis.horizontal,
            itemBuilder: (_, index) {
              final category = categoryController.categories[index];
              return VerticalImageText(
                title: category.attribute,
                image: category.image,
                onTap: () =>
                    Get.to(() => SubCategoriesScreen(category: category)),
              );
            },
          ),
        );
      });
    });
  }
}
