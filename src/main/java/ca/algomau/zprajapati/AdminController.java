package ca.algomau.zprajapati;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.annotation.PostConstruct;

@Controller
public class AdminController {

	
    @Autowired
    private DishService dishService;

    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin/login";
    }

    @GetMapping("/admin/adminhome")
    public String showAdminHomePage(Model model) {
        model.addAttribute("dishes", dishService.getDishesFromDatabase());
        return "admin/adminhome";
    }

    @GetMapping("/admin/addDish")
    public String showAddDishPage(Model model) {
        model.addAttribute("dish", new Dish()); // Use "dish" instead of "dishes" for clarity
        return "admin/addDish";
    }
    

    @PostMapping("/admin/addDish")
    public String addDish(@Validated @ModelAttribute("dish") Dish dish, BindingResult result, 
                          @RequestParam("imagePath") MultipartFile imageFile, 
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Please correct the errors in the form.");
            return "redirect:/admin/addDish";  // Reload the form if there are validation errors
        }

        try {
            // Handle image upload
        	if (!imageFile.isEmpty()) {
        		 String uploadDir = "/src/main/resources/upload";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs(); // Create upload directory if it doesn't exist
                }

                // Generate a unique file name
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.\\-]", "_");
                File destinationFile = new File(uploadDir, fileName);
                imageFile.transferTo(destinationFile);

                // Set the image path for retrieval
                String imagePath = "/upload/" + fileName;
                dish.setImagePath(imagePath); 
                System.out.println("Image uploaded to: " + imagePath);
            }

            // Add dish to the database
            dishService.addDishToDatabase(dish);
            redirectAttributes.addFlashAttribute("message", "Dish added successfully!");
            

        } catch (IOException e) {
            e.printStackTrace(); // Log the error
            redirectAttributes.addFlashAttribute("error", "Image upload failed. Please try again.");
            return "admin/addDish"; // Return the form view on error
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An unexpected error occurred. Please try again.");
            return "admin/addDish";
        }

        return "redirect:/admin/adminhome";
    }
}
