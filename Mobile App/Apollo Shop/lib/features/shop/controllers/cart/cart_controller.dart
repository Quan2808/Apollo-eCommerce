import 'package:apolloshop/common/widgets/loaders/loaders.dart';
import 'package:apolloshop/data/models/cart/cart_item_model.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/data/models/product/variant_model.dart';
import 'package:apolloshop/data/repositories/cart/cart_repository.dart';
import 'package:apolloshop/data/services/cart/cart_service.dart';
import 'package:apolloshop/features/shop/controllers/product/variant_controller.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get_storage/get_storage.dart';

class CartController extends GetxController {
  static CartController get instance => Get.find();

  // Variables
  RxInt noOfCartItems = 0.obs;
  RxDouble totalCartPrice = 0.0.obs;
  RxInt productQuantityInCart = 0.obs;
  RxList<CartItemModel> cartItems = <CartItemModel>[].obs;
  final variantController = VariantController.instance;
  final cartRepo = CartRepository.instance;
  final cartService = CartService.instance;

  CartController() {
    loadCartItems();
    ever(variantController.selectedVariant, (_) {
      updateSelectedVariantQuantity();
    });
  }

  void addProductToCart(ProductModel product) {
    if (productQuantityInCart.value < 1) {
      Loaders.warningSnackBar(
        title: 'Select quantity',
        message: 'Please select quantity',
      );
      return;
    }

    if (variantController.selectedVariant.value?.id == null) {
      Loaders.warningSnackBar(
          title: 'Select Variant', message: 'Please select a variant');
      return;
    }

    final selectedCartItem =
        createCartItemFromVariant(product, productQuantityInCart.value);

    if (selectedCartItem != null) {
      int index = cartItems.indexWhere((cartItem) =>
          cartItem.variant?.id == selectedCartItem.variant?.id &&
          cartItem.product?.id == selectedCartItem.product?.id);

      if (index >= 0) {
        cartItems[index].quantity = selectedCartItem.quantity;
      } else {
        cartItems.add(selectedCartItem);
      }

      cartRepo.addCartItem(selectedCartItem);
      updateCart();
      manageCartItem(selectedCartItem.toJson()); // Call manageCartItem

      Loaders.successSnackBar(
        title: 'Successfully',
        message: 'Your product has been added to cart',
      );
    }
  }

  CartItemModel? createCartItemFromVariant(ProductModel product, int quantity) {
    variantController.fetchVariantsByProduct(product.id);
    cartRepo.fetchCurrentCart();

    if (variantController.variants.isEmpty) {
      variantController.resetSelectedVariant();
      return null;
    }

    final variant = variantController.selectedVariant.value;
    if (variant == null) {
      return null;
    }

    final selectedVariants = {
      'id': variant.id,
      'name': variant.name,
      'skuCode': variant.skuCode,
      'stockQuantity': variant.quantity,
      'weight': variant.weight,
      'price': variant.price,
      'salePrice': variant.salePrice,
      'img': variant.img,
      'isDeleted': variant.isDeleted,
    };

    return CartItemModel(
      product: product,
      quantity: quantity,
      variant: variant,
      price: variant.price,
      store: product.store,
      cart: cartRepo.currentCart,
      selectedVariants: selectedVariants,
    );
  }

  void updateCart() {
    cartItems.assignAll(cartRepo.cartItems);
    updateCartTotal();
    saveCartItems();
  }

  void updateCartTotal() {
    double setTotalCartPrice = 0.0;
    int setNoOfCartItems = 0;

    for (var item in cartItems) {
      setTotalCartPrice += item.price * item.quantity.toDouble();
      setNoOfCartItems += item.quantity;
    }

    totalCartPrice.value = setTotalCartPrice;
    noOfCartItems.value = setNoOfCartItems;
  }

  void saveCartItems() {
    final cartItemsList = cartItems.map((item) => item.toJson()).toList();
    GetStorage().write('cartItems', cartItemsList);
  }

  void loadCartItems() {
    final cartItemsList = GetStorage().read<List>('cartItems');
    if (cartItemsList != null) {
      cartItems.assignAll(cartItemsList
          .map((item) => CartItemModel.fromJson(item as Map<String, dynamic>)));
      updateCartTotal();
    }
  }

  int getProductQuantity(ProductModel product) {
    return cartItems
        .where((item) => item.product?.id == product.id)
        .fold(0, (prev, e) => prev + e.quantity);
  }

  int getVariantQuantity(VariantModel variant) {
    return cartItems
        .firstWhere((item) => item.variant?.id == variant.id,
            orElse: () => CartItemModel.empty())
        .quantity;
  }

  void clearCart() async {
    productQuantityInCart.value = 0;
    cartRepo.clearCart();
    updateCart();
  }

  void incrementCartItem(CartItemModel item) {
    cartRepo.addCartItem(item.copyWith(quantity: 1));
    updateCart();
    manageCartItem(item
        .copyWith(quantity: cartRepo.getVariantQuantity(item.variant!))
        .toJson());
  }

  void decrementCartItem(CartItemModel item) {
    if (cartRepo.getVariantQuantity(item.variant!) > 0) {
      cartRepo.addCartItem(item.copyWith(quantity: -1));
      updateCart();
      manageCartItem(item
          .copyWith(quantity: cartRepo.getVariantQuantity(item.variant!))
          .toJson());
    } else {
      removeFromCartDialog(item);
    }
  }

  void removeFromCartDialog(CartItemModel item) {
    Get.defaultDialog(
      title: 'Remove from Cart',
      middleText: 'Are you sure you want to remove this item from your cart?',
      textConfirm: 'Yes',
      textCancel: 'Cancel',
      confirmTextColor: Colors.white,
      onConfirm: () {
        cartRepo.removeCartItem(item.copyWith(quantity: 0));
        updateCart();
        Loaders.successSnackBar(
          title: 'Success',
          message: 'Item has been removed from the cart.',
        );
        Navigator.of(Get.overlayContext!).pop();
      },
      onCancel: () => Get.closeAllSnackbars(),
      barrierDismissible: false,
      radius: 10.0,
    );
  }

  void updateSelectedVariantQuantity() {
    final variant = variantController.selectedVariant.value;
    if (variant != null) {
      productQuantityInCart.value = getVariantQuantity(variant);
    } else {
      productQuantityInCart.value = 0;
    }
  }

  Future<void> manageCartItem(Map<String, dynamic> cartItem) async {
    try {
      await cartService.manageCartItem(cartItem);
      await cartRepo.fetchCartItems(); // Refresh cart items from server
      updateCart(); // Update UI
    } catch (e) {
      Loaders.errorSnackBar(
        title: 'Error',
        message: 'An error occurred. Please try again.',
      );
    }
  }
}
