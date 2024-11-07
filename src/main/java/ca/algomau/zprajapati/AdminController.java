package ca.algomau.zprajapati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    private DishService dishService;

    @Value("${upload.dir}")
    private String uploadDir;

    @PostConstruct
    public void init() {
        System.out.println("Upload directory: " + uploadDir);
    }
    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin/login";
    }

    @GetMapping("/admin/adminhome")
    public String showAdminHomePage(Model model) {
        model.addAttribute("dishes", dishService.getDishes());
        return "admin/adminhome";
    }

    @PostMapping("/admin/deleteDish")
    public String deleteDish(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
        boolean removed = dishService.getDishes().removeIf(dish -> dish.getName().equals(name));
        if (removed) {
            redirectAttributes.addFlashAttribute("message", "Dish deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Dish not found!");
        }
        return "redirect:/admin/adminhome";
    }
    @GetMapping("/admin/addDish")
    public String showAddDishPage(Model model) {
        model.addAttribute("dish", new Dish());  // Create an empty Dish object for the form
        return "admin/addDish";  // The view for adding the dish (create this HTML form in your templates)
    }


    @PostMapping("/admin/addDish")
    public String addDish(@ModelAttribute Dish dish, @RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        if (dish.getName() == null || dish.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Dish name cannot be empty.");
            return "redirect:/admin/adminhome";
        }
        if (dish.getPrice() < 0) {
            redirectAttributes.addFlashAttribute("error", "Price cannot be negative.");
            return "redirect:/admin/adminhome";
        }

        if (!imageFile.isEmpty()) {
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File destinationFile = new File(uploadDir + fileName);
            try {
                imageFile.transferTo(destinationFile);
                dish.setImagePath("/upload/" + fileName);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", "Failed to upload image. Please try again.");
                return "redirect:/admin/adminhome";
            }
        }

        dishService.addDish(dish);
        redirectAttributes.addFlashAttribute("message", "Dish added successfully!");
        return "redirect:/admin/adminhome";
    }
}
