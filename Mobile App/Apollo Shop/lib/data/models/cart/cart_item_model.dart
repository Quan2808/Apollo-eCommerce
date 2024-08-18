import 'package:apolloshop/data/models/cart/cart_model.dart';
import 'package:apolloshop/data/models/product/product_model.dart';
import 'package:apolloshop/data/models/product/variant_model.dart';
import 'package:apolloshop/data/models/store/store_model.dart';

class CartItemModel {
  int? id;
  int quantity;
  double price;
  CartModel? cart;
  ProductModel? product;
  StoreModel? store;
  VariantModel? variant;
  Map<String, dynamic>? selectedVariants;

  CartItemModel({
    this.id,
    required this.quantity,
    double? price,
    this.product,
    this.store,
    this.cart,
    this.variant,
    this.selectedVariants,
  }) : price = price ?? variant?.price ?? 0.0;

  static CartItemModel empty() {
    return CartItemModel(
      quantity: 0,
      product: null,
    );
  }

  factory CartItemModel.fromJson(Map<String, dynamic> json) {
    VariantModel? variant = json['variantDto'] != null
        ? VariantModel.fromJson(json['variantDto'])
        : null;

    return CartItemModel(
      id: json['id'] ?? 0,
      quantity: json['quantity'] ?? 0,
      price: (json['price'] as num?)?.toDouble() ?? variant?.price ?? 0.0,
      cart:
          json['cartDto'] != null ? CartModel.fromJson(json['cartDto']) : null,
      product: json['productDto'] != null
          ? ProductModel.fromJson(json['productDto'])
          : null,
      store: json['storeDto'] != null
          ? StoreModel.fromJson(json['storeDto'])
          : null,
      variant: variant,
      selectedVariants: json['selectedVariants'] as Map<String, dynamic>?,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'quantity': quantity,
      'price': variant?.price ?? price,
      'cartDto': cart?.toJson(),
      'productDto': product?.toJson(),
      'storeDto': store?.toJson(),
      'variantDto': variant?.toJson(),
      'selectedVariants': selectedVariants,
    };
  }

  CartItemModel copyWith({
    int? id,
    int? quantity,
    double? price,
    CartModel? cart,
    ProductModel? product,
    StoreModel? store,
    VariantModel? variant,
    Map<String, dynamic>? selectedVariants,
  }) {
    return CartItemModel(
      id: id ?? this.id,
      quantity: quantity ?? this.quantity,
      price: price ?? variant?.price ?? this.price,
      cart: cart ?? this.cart,
      product: product ?? this.product,
      store: store ?? this.store,
      variant: variant ?? this.variant,
      selectedVariants: selectedVariants ?? this.selectedVariants,
    );
  }
}
