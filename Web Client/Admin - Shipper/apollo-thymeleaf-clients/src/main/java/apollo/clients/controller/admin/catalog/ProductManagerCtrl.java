package apollo.clients.controller.admin.catalog;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import apollo.clients.controller.admin.AdminController;
import apollo.clients.dto.catalog.ImageUploadForm;
import apollo.clients.dto.catalog.OptionTableDTO;
import apollo.clients.dto.catalog.OptionValueDTO;
import apollo.clients.dto.catalog.ProductDTO;
import apollo.clients.dto.catalog.ReviewFormDTO;
import apollo.clients.dto.catalog.VariantDTO;
import apollo.clients.dto.catalog.VariantUpdateRequest;
import apollo.clients.request.ImageCreateResponse;
import apollo.clients.request.ReviewResponse;
import apollo.clients.service.catalog.CategoryService;
import apollo.clients.service.catalog.ProductService;
import apollo.clients.service.catalog.StoreCategoryService;
import apollo.clients.service.catalog.StoreService;
import apollo.clients.service.catalog.VariantService;
import apollo.clients.utils.FileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dashboard/catalog/products")
public class ProductManagerCtrl {

    private static final Logger logger = LoggerFactory.getLogger(ProductManagerCtrl.class);
    private static final String productDirect = "admin/pages/catalog/products/";

    private final ProductService service;
    private final VariantService variantService;
    private final CategoryService categoryService;
    private final StoreService storeService;
    private final StoreCategoryService storeCategoryService;

    public ProductManagerCtrl(ProductService service, VariantService variantService, CategoryService categoryService,
            StoreService storeService, StoreCategoryService storeCategoryService) {
        this.service = service;
        this.variantService = variantService;
        this.categoryService = categoryService;
        this.storeService = storeService;
        this.storeCategoryService = storeCategoryService;
    }

    @GetMapping
    public String products(Model model) {
        String[] breadcrumb = { "Catalog", "Products" };
        try {
            List<Map<String, Object>> products = service.getProducts();
            model.addAttribute("products", products);
        } catch (Exception e) {
            logger.error("Error fetching products", e);
            model.addAttribute("error", "Failed to load products.");
        }
        return AdminController.renderView(model, productDirect + "index", "Products", breadcrumb);
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable("id") int id, Model model) {
        String[] breadcrumb = { "Catalog", "Products", "Detail" };
        try {
            ProductDTO product = service.getProductById(id);
            model.addAttribute("product", product);
            model.addAttribute("variant", new VariantDTO());
            model.addAttribute("variants", variantService.getVariantsByProductId(id));
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("store_categories", storeCategoryService.getAllStoreCategories());
            model.addAttribute("options", service.getOptionsByProductId((long) id));

            // Lấy OptionValue dựa trên Product ID
            List<OptionValueDTO> optionValues = service.getOptionValuesByProductId((long) id);
            model.addAttribute("optionValues", optionValues);
            model.addAttribute("optionValueDTO", new OptionValueDTO());
            model.addAttribute("selectedOptionId", Long.valueOf(0)); // Mặc định là 0

            // Thêm form review
            model.addAttribute("reviewForm", new ReviewFormDTO());

            // Lấy danh sách review theo Product ID
            ReviewResponse reviewResponse = service.getReviewsByProductId((long) id);
            model.addAttribute("reviews", reviewResponse.getReviewDTOList());
            model.addAttribute("summary", reviewResponse.getSummaryDto());

        } catch (Exception e) {
            logger.error("Error fetching product details for ID: " + id, e);
            model.addAttribute("error", "Failed to load product details.");
        }
        return AdminController.renderView(model, productDirect + "detail", "Product Detail", breadcrumb);
    }

    @PostMapping("/save/variant/{productId}")
    public String createVariantForProduct(@PathVariable int productId, @ModelAttribute VariantDTO variantDTO,
            @RequestParam(value = "imgPatch", required = false) MultipartFile imgPatch, Model model) {
        try {
            if (imgPatch != null && !imgPatch.isEmpty()) {
                String imagePath = FileUploadUtil.saveFile(imgPatch, "variants", variantDTO.getName());
                variantDTO.setImg(imagePath);
            }
            variantDTO.setImgPatch(null);
            variantService.createVariantForProduct(productId, variantDTO);
            model.addAttribute("message", "Variant created successfully.");
        } catch (Exception e) {
            logger.error("Error creating variant for product ID: " + productId, e);
            model.addAttribute("error", "Failed to create variant.");
        }
        return getProductById(productId, model);
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        String[] breadcrumb = { "Catalog", "Products", "Add Product" };
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("store_categories", storeCategoryService.getAllStoreCategories());
        return AdminController.renderView(model, productDirect + "new", "Add Product", breadcrumb);
    }

    @GetMapping("/update-variant/{variantId}")
    public String updateVariantForm(@PathVariable Long variantId, Model model) {
        String[] breadcrumb = { "Catalog", "Products", "Update Variant" };

        try {
            // Fetch the variant details that need to be updated
            VariantDTO variantDTO = service.getVariantByVariantId(variantId);
            model.addAttribute("variantDTO", variantDTO);
        } catch (Exception e) {
            logger.error("Error fetching variant with ID: " + variantId, e);
            model.addAttribute("error", "Failed to load variant details.");
        }

        model.addAttribute("breadcrumb", breadcrumb);
        return AdminController.renderView(model, productDirect + "update-variant", "", breadcrumb);
    }

    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductDTO productDTO,
            @RequestParam(value = "mainPicturePatch", required = false) MultipartFile mainPicturePatch, Model model) {
        try {
            service.createProduct(productDTO, mainPicturePatch); // Pass both the product and the file
            model.addAttribute("message", "Product created successfully.");
            return "redirect:/dashboard/catalog/products";
        } catch (Exception e) {
            logger.error("Error creating product", e);
            model.addAttribute("error", "Failed to create product.");
            return newProductForm(model);
        }
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable int id, @ModelAttribute ProductDTO productDTO,
            @RequestParam(value = "mainPicturePatch", required = false) MultipartFile mainPicturePatch, Model model) {
        try {
            // Retrieve existing product details from the service
            ProductDTO existingProduct = service.getProductById(id);

            // Update product fields with new values from productDTO
            existingProduct.setTitle(productDTO.getTitle());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setStatus(productDTO.getStatus());

            // Optionally, update the category and store if needed
            // Remove these if your API does not require them
            if (productDTO.getCategory() != null) {
                existingProduct.setCategory(productDTO.getCategory());
            }
            if (productDTO.getStore() != null) {
                existingProduct.setStore(productDTO.getStore());
            }
            if (productDTO.getStoreCategory() != null) {
                existingProduct.setStoreCategory(productDTO.getStoreCategory());
            }

            // Check if there is a new image uploaded
            if (mainPicturePatch != null && !mainPicturePatch.isEmpty()) {
                // If a new image is provided, update the product with the new image
                existingProduct.setMainPicturePatch(mainPicturePatch);
                service.updateProduct((long) id, existingProduct, mainPicturePatch);
            } else {
                // If no new image is provided, update the product without changing the image
                service.updateProduct((long) id, existingProduct);
            }

            // Add a success message to the model
            model.addAttribute("message", "Product updated successfully.");
            // Redirect to the products catalog page
            return "redirect:/dashboard/catalog/products";
        } catch (Exception e) {
            // Log the error and add an error message to the model
            logger.error("Error updating product ID: " + id, e);
            model.addAttribute("error", "Failed to update product.");
            // Return to the product detail page in case of an error
            return getProductById(id, model);
        }
    }

    @PostMapping("/{product_id}/option/create")
    public String createOption(@ModelAttribute OptionTableDTO optionTableDto, @PathVariable("product_id") Long id,
            Model model) {
        logger.debug("Creating options with Product ID: {}", id);
        logger.debug("Options names: {}", optionTableDto.getName());

        try {
            List<OptionTableDTO> options = service.createOptions(optionTableDto.getName(), id);
            model.addAttribute("options", options);
            return "redirect:/dashboard/catalog/products/" + id;
        } catch (Exception e) {
            logger.error("Error creating options", e);
            model.addAttribute("error", "Failed to create options.");
            return "error-page";
        }
    }

    @PostMapping("/create-option-value")
    public String createOptionValue(@RequestParam("productId") Long productId,
            @RequestParam("selectedOptionId") Long optionId,
            @RequestParam("values") List<String> values, Model model, HttpServletRequest request) {
        try {
            List<OptionValueDTO> optionValues = service.createOptionValue(values, optionId);
            model.addAttribute("message", "Option values created successfully.");
            model.addAttribute("optionValues", optionValues);
        } catch (Exception e) {
            logger.error("Error creating option values for option ID: " + optionId, e);
            model.addAttribute("error", "Failed to create option values.");
        }
        // Lấy URL của trang trước đó từ header "Referer"
        String refererUrl = request.getHeader("Referer");
        return "redirect:" + (refererUrl != null ? refererUrl : "/dashboard/catalog/products" + productId);
    }

    @PostMapping("/create-variant")
    public String createVariant(
            @RequestParam(value = "productId", required = true) Long productId,
            @RequestParam("optionValues") List<Long> optionValues,
            RedirectAttributes redirectAttributes) {

        if (productId == null) {
            logger.error("Received null productId");
            redirectAttributes.addFlashAttribute("errorMessage", "Product ID is required.");
            return "redirect:/dashboard/catalog/products/";
        }

        try {
            VariantDTO variantDto = service.createRawVariant(optionValues, productId);
            redirectAttributes.addFlashAttribute("successMessage", "Variant created successfully.");
        } catch (Exception e) {
            logger.error("Error creating variant for productId: " + productId, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create variant.");
        }
        return "redirect:/dashboard/catalog/products/" + productId;
    }

    @PostMapping("/update-variant")
    public String updateVariant(
            @RequestParam Long variantId,
            @ModelAttribute VariantDTO variantDto,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        try {
            // Tạo một đối tượng VariantUpdateRequest để chứa variantId và variantDto
            VariantUpdateRequest updateRequest = new VariantUpdateRequest();
            updateRequest.setVariantId(variantId);
            updateRequest.setName(variantDto.getName());
            updateRequest.setSkuCode(variantDto.getSkuCode());
            updateRequest.setStockQuantity(variantDto.getStockQuantity());
            updateRequest.setWeight(variantDto.getWeight());
            updateRequest.setPrice(variantDto.getPrice());
            updateRequest.setSalePrice(variantDto.getSalePrice());
            updateRequest.setImg(variantDto.getImg());

            // Gọi service để cập nhật variant với file ảnh
            VariantDTO updatedVariant = service.updateVariant(updateRequest, imageFile);

            redirectAttributes.addFlashAttribute("successMessage", "Variant updated successfully.");
        } catch (Exception e) {
            logger.error("Error updating variant with ID: " + variantId, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update variant.");
        }

        // Lấy URL của trang trước đó từ header "Referer"
        String refererUrl = request.getHeader("Referer");
        return "redirect:" + (refererUrl != null ? refererUrl : "/dashboard/catalog/products" + variantId);
    }

    @PostMapping("/review-variant")
    public String submitReviewVariant(@ModelAttribute("reviewForm") ReviewFormDTO reviewFormDTO,
            @RequestParam("variantId") Long variantId,
            RedirectAttributes redirectAttributes) {
        Long userId = 1L; // Gán cố định userId là 1
        try {
            // Gọi service để submit review
            service.submitReviewToApi(reviewFormDTO, variantId, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Review created successfully.");
        } catch (Exception e) {
            logger.error("Error creating review for variant ID: " + variantId, e);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create review.");
        }
        return "redirect:/dashboard/catalog/products";
    }

    @GetMapping("/review-variant/{variantId}")
    public String showReviewForm(@PathVariable("variantId") Long variantId, Model model) {
        String[] breadcrumb = { "Catalog", "Products", "Review" };
        try {
            // Gọi service để lấy thông tin variant theo variantId
            VariantDTO variantDTO = service.getVariantByVariantId(variantId);

            model.addAttribute("variantDTO", variantDTO);
            model.addAttribute("reviewForm", new ReviewFormDTO());
        } catch (Exception e) {
            logger.error("Error loading review form for variant ID: " + variantId, e);
            model.addAttribute("error", "Failed to load review form.");
        }
        return AdminController.renderView(model, productDirect + "review-variant",
                "Recommendations from the Manufacturer", breadcrumb);
    }

    @GetMapping("/variant-image-list/{variantId}")
    public String showVariantImageForm(@PathVariable("variantId") Long variantId, Model model) {
        String[] breadcrumb = { "Catalog", "Products", "Images" };
        VariantDTO variantDTO = service.getVariantByVariantId(variantId);

        // Tạo một đối tượng ImageUploadForm và đặt variantId
        ImageUploadForm form = new ImageUploadForm();
        form.setVariantId(variantId);

        model.addAttribute("variantDTO", variantDTO);
        model.addAttribute("imageUploadForm", form);

        return AdminController.renderView(model, productDirect + "variant-image-list", " ", breadcrumb);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("files") MultipartFile[] files,
            @RequestParam("variantId") Long variantId,
            Model model) throws IOException {
        logger.info("Received request to upload files for variant ID: " + variantId);
        ImageCreateResponse response = service.uploadImages(files, variantId);
        model.addAttribute("imageIds", response.getImageIds());
        model.addAttribute("message", response.getMessage());
        return "redirect:/dashboard/catalog/products";
    }

}
