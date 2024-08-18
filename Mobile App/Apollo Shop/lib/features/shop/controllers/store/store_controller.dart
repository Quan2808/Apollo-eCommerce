import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:apolloshop/data/models/store/store_model.dart';
import 'package:apolloshop/data/repositories/product/product_repository.dart';
import 'package:apolloshop/data/repositories/store/store_repository.dart';
import 'package:get/get.dart';

class StoreController extends GetxController {
  static StoreController get instance => Get.find();

  final isLoading = false.obs;

  final RxList<StoreModel> stores = <StoreModel>[].obs;
  final RxList<StoreModel> featuredStores = <StoreModel>[].obs;
  final RxMap<int, List<StoreModel>> storesByCategory =
      <int, List<StoreModel>>{}.obs;
  final RxMap<int, int> productsCountByStore = <int, int>{}.obs;
  final RxMap<int, List<String>> productThumbnailsByStore = <int, List<String>>{}.obs;

  final StoreRepository _storeRepository = Get.find();
  final ProductRepository _productRepository = Get.find();

  @override
  void onInit() {
    fetchProductThumbnailsByStore();
    fetchStores();
    fetchStoresByCategory();
    fetchProductsCountByStore();
    super.onInit();
  }

  Future<void> fetchStores() async {
    try {
      isLoading.value = true;

      // Fetch stores
      final getStores = await _storeRepository.getStores();

      // Assign fetched Stores to the observable list
      stores.assignAll(getStores);
      featuredStores.assignAll(stores.take(4));
    } catch (e) {
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
    } finally {
      isLoading.value = false;
    }
  }

  Future<void> fetchStoresByCategory() async {
    try {
      isLoading.value = true;

      // Fetch all products
      final products = await _productRepository.getProducts();

      // Group stores by category
      final Map<int, List<StoreModel>> groupedStores = {};
      for (var product in products) {
        if (product.category != null) {
          final categoryId = product.category!.id;
          if (product.store != null) {
            if (!groupedStores.containsKey(categoryId)) {
              groupedStores[categoryId] = [];
            }
            // Add store to the category's store list if not already present
            if (!groupedStores[categoryId]!
                .any((store) => store.id == product.store!.id)) {
              groupedStores[categoryId]!.add(product.store!);
            }
          }
        }
      }

      // Assign grouped stores to the observable map
      storesByCategory.assignAll(groupedStores);
    } catch (e) {
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
    } finally {
      isLoading.value = false;
    }
  }

  Future<void> fetchProductsCountByStore() async {
    try {
      isLoading.value = true;

      // Fetch all products
      final products = await _productRepository.getProducts();

      // Count products by store
      final Map<int, int> countByStore = {};
      for (var product in products) {
        if (product.store != null) {
          final storeId = product.store!.id;
          if (!countByStore.containsKey(storeId)) {
            countByStore[storeId] = 0;
          }
          countByStore[storeId] = countByStore[storeId]! + 1;
        }
      }

      // Assign product counts to the observable map
      productsCountByStore.assignAll(countByStore);
    } catch (e) {
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
    } finally {
      isLoading.value = false;
    }
  }

  Future<void> fetchProductThumbnailsByStore() async {
    try {
      isLoading.value = true;

      // Fetch all products
      final products = await _productRepository.getProducts();

      // Group product thumbnails by store
      final Map<int, List<String>> groupedThumbnails = {};
      for (var product in products) {
        if (product.store != null) {
          final storeId = product.store!.id;
          if (!groupedThumbnails.containsKey(storeId)) {
            groupedThumbnails[storeId] = [];
          }
          if (groupedThumbnails[storeId]!.length < 3) {
            groupedThumbnails[storeId]!.add(product.thumbnail);
          }
        }
      }

      // Assign grouped thumbnails to the observable map
      productThumbnailsByStore.assignAll(groupedThumbnails);
    } catch (e) {
      Loaders.errorSnackBar(title: 'Oh Snap!', message: e.toString());
    } finally {
      isLoading.value = false;
    }
  }

  List<String> getProductThumbnailsByStore(int storeId) {
    return productThumbnailsByStore[storeId] ?? [];
  }

  List<StoreModel> getStoresByCategory(int categoryId) {
    return storesByCategory[categoryId] ?? [];
  }


}
