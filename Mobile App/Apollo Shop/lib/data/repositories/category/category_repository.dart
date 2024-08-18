import 'package:apolloshop/data/models/category/category_model.dart';
import 'package:apolloshop/data/services/category/category_service.dart';
import 'package:get/get.dart';

class CategoryRepository extends GetxService {
  static CategoryRepository get instance => Get.find();

  final CategoryService _service = Get.find<CategoryService>();

  Future<List<CategoryModel>> getCategories() async {
    final categoriesData = await _service.getCategories();
    return categoriesData.map((data) => CategoryModel.fromJson(data)).toList();
  }
}
