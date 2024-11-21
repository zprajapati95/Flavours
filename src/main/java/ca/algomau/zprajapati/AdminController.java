package ca.algomau.zprajapati;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public AdminController(DishRepository dishRepository) {
		super();
		this.dishRepository = dishRepository;
	}

	@GetMapping("/admin/login")
	public String showLoginPage() {
		return "admin/login";
	}

	@GetMapping("/admin/adminhome")
	public String showAdminHomePage(Model model) {
	    model.addAttribute("dish", new Dish());
	    model.addAttribute("categories", List.of("BreakFast Menu", "Lunch Menu", "Dinner Menu", "Wine List","Beverages List")); // Add your categories
	    model.addAttribute("dishes", dishRepository.getDishes());
	    return "admin/adminhome";
	}


	@PostMapping("/updateDish")
	public String updateDish(@Validated @ModelAttribute Dish dish, BindingResult result,
	                         RedirectAttributes redirectAttributes) {
	    if (result.hasErrors()) {
	        return "admin/editDish"; // Return to the edit form with validation errors
	    }

	    Dish existingDish = dishRepository.getDishById(dish.getId().intValue());
	    if (existingDish == null) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Dish not found for update!");
	        return "redirect:/admin/adminhome";
	    }

	    // Update the dish using the repository
	    dishRepository.editDish(dish);
	    redirectAttributes.addFlashAttribute("message", "Dish updated successfully!");
	    return "redirect:/admin/adminhome";
	}


	@GetMapping("/admin/editDish/{id}")
	public String showEditDishForm(@PathVariable("id") Long id, Model model) {
		// Fetch the dish by ID
		Dish dish = dishRepository.getDishById(id.intValue());

		if (dish != null) {
			// Add the dish object to the model so it can be pre-populated in the form
			model.addAttribute("dish", dish);
			return "admin/editDish"; // Return to the editDish.html page
		} else {
			return "redirect:/admin/adminhome"; // If dish not found, redirect to admin home
		}
	}

	@PostMapping("/addDish1")
	public String addDish1(@Validated @ModelAttribute Dish dish, BindingResult result,
			RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "admin/adminhome"; // Return form with validation errors
		}

		dishRepository.addDish(dish);
		redirectAttributes.addFlashAttribute("message", "Dish added successfully!");
		return "redirect:/admin/adminhome";
	}

	

	@DeleteMapping("/deleteDish/{id}")
	public ResponseEntity<String> deleteDish(@PathVariable("id") Long id) {
		System.out.println("Attempting to delete dish with ID: " + id);

		try {
			// Directly check if the dish exists in the repository
			String sql = "SELECT COUNT(*) FROM dishes WHERE id = ?";
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

			if (count != null && count > 0) {
				dishRepository.deleteById(id);
				System.out.println("Dish deleted successfully with ID: " + id);
				return ResponseEntity.ok("Dish deleted successfully");
			} else {
				System.out.println("Dish not found with ID: " + id);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dish not found");
			}
		} catch (Exception e) {
			System.out.println("Error during delete operation: " + e.getMessage());
			e.printStackTrace(); // Print the stack trace for debugging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting dish");
		}
	}

	// Controller method to fetch and display all reservations
    @GetMapping("/adminreservation")
    public String showReservations(Model model) {
        // Fetching all reservations from the repository
        List<Reservation> reservations = reservationRepository.getAllReservations();
        // Passing the reservations list to the model
        model.addAttribute("reservations", reservations);
        // Returning the name of the HTML view to be rendered (adminreservation.html)
        return "admin/adminreservation";
    }
	
}
