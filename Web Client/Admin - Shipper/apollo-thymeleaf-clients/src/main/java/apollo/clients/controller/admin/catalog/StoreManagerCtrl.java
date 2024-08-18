package apollo.clients.controller.admin.catalog;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import apollo.clients.controller.admin.AdminController;
import apollo.clients.dto.catalog.StoreCategoryDTO;
import apollo.clients.dto.catalog.StoreDTO;
import apollo.clients.request.AddStoreCateRequest;
import apollo.clients.request.AddStoreRequest;
import apollo.clients.service.catalog.StoreCategoryService;
import apollo.clients.service.catalog.StoreService;
import apollo.clients.utils.JwtUtils;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard/catalog/stores")
public class StoreManagerCtrl {

    private static final String pageDirect = "admin/pages/catalog/stores/";

    private final StoreService service;

    private final StoreCategoryService storeCategoryService;

    public StoreManagerCtrl(StoreService service, StoreCategoryService storeCategoryService) {
        this.service = service;
        this.storeCategoryService = storeCategoryService;
    }

    @GetMapping()
    public String stores(Model model) {
        String[] breadcrumb = { "Catalog", "Stores" };
        try {
            List<StoreDTO> stores = service.getAllStores();
            model.addAttribute("stores", stores);
        } catch (Exception ignored) {
        }

        return AdminController.renderView(model, pageDirect + "index", "Stores", breadcrumb);
    }


    @GetMapping("/create-store-category")
    public String newStoreCategoryForm(Model model) {
        String[] breadcrumb = {"Catalog", "Stores", "create-store-categories"};

        // Fetch all stores and store categories
        List<StoreDTO> stores = service.getAllStores();
        List<StoreCategoryDTO> storeCategories = storeCategoryService.getAllStoreCategoryDTOs();

        // Add data to the model
        model.addAttribute("stores", stores);
        model.addAttribute("storeCategories", storeCategories);
        model.addAttribute("addStoreCateRequest", new AddStoreCateRequest());

        return AdminController.renderView(model, pageDirect + "create_store_categories", "Create Store Categories", breadcrumb);
    }

    @PostMapping("/create-store-categories")
    public String createStoreCategory(@ModelAttribute AddStoreCateRequest addStoreCateRequest, Model model, HttpSession session) {
        String[] breadcrumb = { "Catalog", "Stores", "Create Store Categories" };
        try {
            String token = (String) session.getAttribute("jwtToken");
            Long adminId = Long.parseLong(JwtUtils.decodeSubJWT(token));

            // Call the service to create the category
            storeCategoryService.createStoreCategory(addStoreCateRequest);

            // Redirect on success
            return "redirect:/dashboard/catalog/stores";
        } catch (Exception e) {
            // Handle errors
            model.addAttribute("error", "Create new store category failed: " + e.getMessage());
            model.addAttribute("addStoreCateRequest", addStoreCateRequest);
            return AdminController.renderView(model, pageDirect + "create_store_categories", "Create Store Categories", breadcrumb);
        }
    }


    @GetMapping("/{id}")
    public String getStoreById(@PathVariable("id") int id, Model model) {
        String[] breadcrumb = { "Catalog", "Stores", "Detail" };

        StoreDTO store = service.getStoreById(id);

        model.addAttribute("storeCategoryDTO", new StoreCategoryDTO());
        model.addAttribute("store", store);
        model.addAttribute("categories", storeCategoryService.getStoreCategoriesByStoreId(id));

        return AdminController.renderView(model, pageDirect + "detail", "Store Detail - " + store.getName(),
                breadcrumb);
    }

    @GetMapping("/new")
    public String newStoreForm(Model model) {
        String[] breadcrumb = { "Catalog", "Stores", "Add Store" };
        model.addAttribute("store", new StoreDTO());
        return AdminController.renderView(model, pageDirect + "new", "Add Store", breadcrumb);
    }

    @PostMapping()
    public String createStore(@ModelAttribute AddStoreRequest storeRequest, Model model, HttpSession session) {
        try {
            String token = (String) session.getAttribute("jwtToken");
            Long adminId = Long.parseLong(JwtUtils.decodeSubJWT(token));

            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setName(storeRequest.getName());

            service.createStore(adminId, storeDTO, storeRequest.getDealsImage(), storeRequest.getHomeImage(),
                    storeRequest.getDealsSquareImage(), storeRequest.getInteractiveImage(), storeRequest.getLogo());

            return "redirect:/dashboard/catalog/stores";
        } catch (Exception e) {
            model.addAttribute("error", "Create new store failed: " + e.getMessage());
            return newStoreForm(model);
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteStore(@PathVariable("id") int id, Model model) {
        try {
            service.deleteStore(id);
            return "redirect:/dashboard/catalog/categories";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể xóa store");
            return stores(model);
        }
    }

}