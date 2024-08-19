package apollo.clients.controller.admin.catalog;

import apollo.clients.controller.admin.AdminController;
import apollo.clients.dto.catalog.CategoryDTO;
import apollo.clients.service.catalog.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard/catalog")
public class CategoryManagerCtrl {

    private static final String catalogDirect = "admin/pages/catalog/";

    private final CategoryService categoryService;

    public CategoryManagerCtrl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        String[] breadcrumb = {"Catalog", "Categories"};

        try {
            List<Map<String, Object>> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải danh sách categories");
        }

        return AdminController.renderView(model, catalogDirect + "categories/index", "Categories", breadcrumb);
    }

    @GetMapping("/categories/{id}")
    public String getCategoryById(@PathVariable("id") int id, Model model) {
        String[] breadcrumb = {"Catalog", "Categories", "Detail"};

        try {
            Map<String, Object> category = categoryService.getCategoryById(id);
            model.addAttribute("category", category);
        } catch (Exception e) {
            model.addAttribute("error", "Không thể tải thông tin category");
        }

        return AdminController.renderView(model, catalogDirect + "categories/detail", "Category Detail", breadcrumb);
    }

    @GetMapping("/categories/new")
    public String newCategoryForm(Model model) {
        String[] breadcrumb = {"Catalog", "Categories", "Add Category"};
        model.addAttribute("category", new CategoryDTO());
        return AdminController.renderView(model, catalogDirect + "categories/new", "Add Category", breadcrumb);
    }

    @PostMapping("/categories")
    public String createCategory(@ModelAttribute CategoryDTO categoryDTO, Model model) {
        try {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("attribute", categoryDTO.getAttribute());
            categoryService.createCategory(categoryMap);
            return "redirect:/dashboard/catalog/categories";
        } catch (DuplicateCategoryAttributeException e) {
            // Bắt lỗi khi attribute bị trùng
            model.addAttribute("attributeError", "Category attribute already exists: " + categoryDTO.getAttribute());
            return newCategoryForm(model);
        } catch (Exception e) {
            // Bắt lỗi chung
            model.addAttribute("error", "Failed to create category: " + e.getMessage());
            return newCategoryForm(model);
        }
    }

    public class DuplicateCategoryAttributeException extends RuntimeException {
        public DuplicateCategoryAttributeException(String message) {
            super(message);
        }
    }

    @GetMapping("/categories/{id}/edit")
    public String editCategoryForm(@PathVariable("id") int id, Model model) {
        String[] breadcrumb = {"Catalog", "Categories", "Edit"};
        try {
            Map<String, Object> category = categoryService.getCategoryById(id);
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setAttribute((String) category.get("attribute"));
            model.addAttribute("category", categoryDTO);
            model.addAttribute("categoryId", id);
        } catch (Exception e) {
        }
        return AdminController.renderView(model, catalogDirect + "categories/form", "Edit Category", breadcrumb);
    }

    @PostMapping("/categories/{id}")
    public String updateCategory(@PathVariable("id") int id, @ModelAttribute CategoryDTO categoryDTO, Model model) {
        model.addAttribute("category", categoryDTO);
        model.addAttribute("categoryId", id);
        String[] breadcrumb = {"Catalog", "Categories", "Edit"};
        try {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("id", id);
            categoryMap.put("attribute", categoryDTO.getAttribute());

            categoryService.updateCategory(id, categoryMap);
            return "redirect:/dashboard/catalog/categories";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể cập nhật category: " + e.getMessage());
            return AdminController.renderView(model, catalogDirect + "categories/form", "Edit Category", breadcrumb);
        }
    }

    @GetMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable("id") int id, Model model) {
        try {
            categoryService.deleteCategory(id);
            return "redirect:/dashboard/catalog/categories";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể xóa category");
            return categories(model);
        }
    }

}
