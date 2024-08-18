import 'package:apolloshop/common/widgets/texts/stores/store_title_text.dart';
import 'package:apolloshop/utils/constants/colors.dart';
import 'package:apolloshop/utils/constants/enums.dart';
import 'package:apolloshop/utils/constants/sizes.dart';
import 'package:flutter/material.dart';
import 'package:iconsax/iconsax.dart';

class StoreTitleWithVerifiedIcon extends StatelessWidget {
  const StoreTitleWithVerifiedIcon({
    super.key,
    this.textColor,
    this.iconColor = TColors.primary,
    this.maxLines = 1,
    this.textAlign = TextAlign.center,
    this.textSize = TextSizes.small,
    required this.title,
  });

  final Color? textColor, iconColor;
  final String title;
  final int maxLines;
  final TextAlign? textAlign;
  final TextSizes textSize;

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisSize: MainAxisSize.min,
      children: [
        Flexible(
          child: BrandTitleText(
            title: title,
            color: textColor,
            maxLines: maxLines,
            textAlign: textAlign,
            textSize: textSize,
          ),
        ),
        const SizedBox(width: TSizes.xs),
        Icon(Iconsax.verify5, color: iconColor, size: TSizes.iconXs),
      ],
    );
  }
}
