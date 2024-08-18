import 'package:apolloshop/common/widgets/shimmers/shimmer_effect.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:apolloshop/utils/helpers/helper_functions.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';

class CircularImage extends StatelessWidget {
  const CircularImage({
    super.key,
    this.width = 56,
    this.height = 56,
    this.overlayColor,
    this.backgroundColor,
    this.fit = BoxFit.cover,
    required this.imageUrl,
    this.isNetworkImage = false,
    this.padding = TSizes.sm,
  });

  final BoxFit? fit;
  final String imageUrl;
  final bool isNetworkImage;
  final Color? overlayColor, backgroundColor;
  final double width, height, padding;

  @override
  Widget build(BuildContext context) {
    final dark = THelperFunctions.isDarkMode(context);
    return Container(
      width: width,
      height: height,
      padding: EdgeInsets.all(padding),
      decoration: BoxDecoration(
        color: backgroundColor ?? (dark ? TColors.black : TColors.white),
        borderRadius: BorderRadius.circular(100),
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(100),
        child: isNetworkImage
            ? CachedNetworkImage(
                fit: fit,
                color: overlayColor,
                imageUrl: imageUrl,
                progressIndicatorBuilder: (context, url, downloadProgress) =>
                    const ShimmerEffect(width: 55, height: 55),
                errorWidget: (context, url, error) => const Icon(Icons.error),
              )
            : Center(
                child: Image(
                  fit: fit,
                  image: AssetImage(imageUrl),
                  color: overlayColor,
                ),
              ),
      ),
    );
  }
}
