package ca.algomau.zprajapati;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    @Autowired
    private DishRepository dishRepository;

    // Default route to home page
    @GetMapping("/")
    public String showHomePage() {
        return "customer/home"; // Adjust as needed to match your template (home.html)
    }

    // Menu Page
    @GetMapping("/menu")
    public String showMenuPage(Model model) {
        List<Dish> dishes = dishRepository.getDishes(); // Fetch dishes directly from the repository
        model.addAttribute("dishes", dishes);
        return "customer/menu"; // Return menu.html template
    }

    // About Us Page
    @GetMapping("/about")
    public String showAboutPage() {
        return "customer/about"; // Return about.html template
    }

    // Contact Page
    @GetMapping("/contact")
    public String showContactPage() {
        return "customer/contact"; // Return contact.html template
    }

    // Reservations Page
    @GetMapping("/reservations")
    public String showReservationPage() {
        return "customer/reservations"; // Return reservations.html template
    }

    // Handle Reservation Submission
    @PostMapping("/submitReservation")
    public String submitReservation(
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam("guests") int guests,
            @RequestParam("phone") String phone,
            Model model) {

        model.addAttribute("date", date);
        model.addAttribute("time", time);
        model.addAttribute("guests", guests);
        model.addAttribute("phone", phone);

        return "customer/reservationConfirmation"; // Redirect to confirmation page
    }
}
