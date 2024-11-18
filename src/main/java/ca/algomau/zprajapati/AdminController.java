package ca.algomau.zprajapati;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
	

	
	@Autowired
	private DishRepository dishRepository;
	@Value("${upload.dir}")
	private String uploadDir;
	
	public AdminController(DishRepository dishRepository) {
		super();
		this.dishRepository = dishRepository;
		// TODO Auto-generated constructor stub
	}

	@GetMapping("/admin/login")
	public String showLoginPage() {
		return "admin/login";
	}

	@GetMapping("/admin/adminhome")
	public String showAdminHomePage(Model model) {
		// Add a new Dish object for the form binding
	    model.addAttribute("dish", new Dish());
	    
	    // Add the list of existing dishes to display
	    model.addAttribute("dishes", dishRepository.getDishes());
		return "admin/adminhome";
	}

	@PostMapping("/admin/adminhome")
	public String addDish(@Validated @ModelAttribute Dish dish, BindingResult result,
			@RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("error", "Please correct the errors in the form.");
			return "redirect:/admin/adminhome";
		}

		try {
			// Ensure the upload directory exists
			File uploadDirFile = new File(uploadDir);
			if (!uploadDirFile.exists() && !uploadDirFile.mkdirs()) {
				throw new IOException("Failed to create upload directory: " + uploadDir);
			}

			// Create a unique file name
			String fileName = System.currentTimeMillis() + "_"
					+ imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.\\-]", "_");
			File destinationFile = new File(uploadDirFile, fileName);

			// Transfer file to the target location
			imageFile.transferTo(destinationFile);

			// Set image path for the Dish
			String imagePath = "/images/" + fileName;
			dish.setImagePath(imagePath);

			// Save to database
			dishRepository.addDish(dish);
			redirectAttributes.addFlashAttribute("message", "Dish added successfully!");

		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Failed to upload image: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
		}

		return "redirect:/admin/adminhome";
	}

}
