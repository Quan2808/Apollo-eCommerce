import 'package:apolloshop/data/models/cart/cart_item_model.dart';
import 'package:apolloshop/data/models/cart/cart_model.dart';
import 'package:apolloshop/data/models/product/variant_model.dart';
import 'package:apolloshop/data/services/cart/cart_service.dart';
import 'package:apolloshop/features/personalization/controllers/user/user_controller.dart';
import 'package:get/get.dart';

class CartRepository extends GetxService {
  static CartRepository get instance => Get.find();

  final CartService _cartService = Get.find<CartService>();

  final Rx<CartModel?> _currentCart = Rx<CartModel?>(null);
  final RxList<CartItemModel> _cartItems = <CartItemModel>[].obs;

  CartModel? get currentCart => _currentCart.value;
  List<CartItemModel> get cartItems => _cartItems;

  @override
  void onInit() async {
    super.onInit();
    await fetchCurrentCart();
    await fetchCartItems();
  }

  Future<void> fetchCurrentCart() async {
    final userId = UserController.instance.user.value!.id;
    final cartData = await _cartService.getCart(user: userId);
    if (cartData != null) {
      _currentCart.value = CartModel.fromJson(cartData);
      await fetchCartItems();
    } else {
      _currentCart.value = null;
      _cartItems.clear();
    }
  }

  Future<void> fetchCartItems() async {
    final userId = UserController.instance.user.value!.id;
    final cartItemsData = await _cartService.getCartItems(userId);
    if (cartItemsData != null) {
      _cartItems.assignAll(
        cartItemsData.map(
            (item) => CartItemModel.fromJson(item as Map<String, dynamic>)),
      );
    }
  }

  void updateCartItems(List<CartItemModel> items) {
    _cartItems.assignAll(items);
  }

  void clearCart() async {
    final userId = UserController.instance.user.value!.id;
    await _cartService.deleteAllCartItems(userId);
    _cartItems.clear();
  }

  void addCartItem(CartItemModel item) {
    int index = _cartItems.indexWhere((cartItem) =>
        cartItem.product?.id == item.product?.id &&
        cartItem.variant?.id == item.variant?.id);

    if (index >= 0) {
      _cartItems[index] = _cartItems[index].copyWith(
        quantity: _cartItems[index].quantity + item.quantity,
      );
    } else {
      _cartItems.add(item);
    }
    _cartItems.refresh();
  }

  void removeCartItem(CartItemModel item) {
    _cartItems.removeWhere((cartItem) =>
        cartItem.product?.id == item.product?.id &&
        cartItem.variant?.id == item.variant?.id);
    _cartItems.refresh();
  }

  int getVariantQuantity(VariantModel variant) {
    return _cartItems
        .firstWhere((item) => item.variant?.id == variant.id,
            orElse: () => CartItemModel.empty())
        .quantity;
  }
}
