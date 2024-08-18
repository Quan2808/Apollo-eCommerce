import 'package:apolloshop/utils/constants/colors.dart';
import 'package:flutter/material.dart';

class SettingsMenuTitle extends StatelessWidget {
  const SettingsMenuTitle({
    super.key,
    required this.title,
    required this.subtitle,
    required this.icon,
    this.trailing,
    this.onTap,
  });

  final String title;
  final String subtitle;
  final IconData icon;
  final Widget? trailing;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context).textTheme;
    return ListTile(
      leading: Icon(
        icon,
        size: 28,
        color: TColors.primary,
      ),
      title: Text(
        title,
        style: theme.titleMedium,
      ),
      subtitle: Text(
        subtitle,
        style: theme.labelMedium,
      ),
      trailing: trailing,
      onTap: onTap,
    );
  }
}
