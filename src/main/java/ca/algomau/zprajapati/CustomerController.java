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
    @Autowired
    private ReservationRepository reservationRepository;

    // Default route to home page
    @GetMapping("/")
    public String showHomePage() {
        return "customer/home"; // Adjust as needed to match your template (home.html)
    }

    @GetMapping("/menu")
    public String showMenuPage(@RequestParam(required = false) String category, Model model) {
        List<Dish> dishes;
        if (category != null && !category.isEmpty()) {
            dishes = dishRepository.getDishesByCategory(category);
        } else {
            dishes = dishRepository.getDishes(); // Show all dishes if no category is selected
        }
        model.addAttribute("categories", List.of("BreakFast Menu", "Lunch Menu", "Dinner Menu", "Wine List","Beverages List")); // Add your categories
        model.addAttribute("dishes", dishes);
        return "customer/menu";
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

        // Create a new Reservation object
        Reservation reservation = new Reservation();
        reservation.setReservationDate(date);
        reservation.setReservationTime(time);
        reservation.setNumberOfGuests(guests);
        reservation.setPhoneNumber(phone);

        // Save the reservation in the database
        reservationRepository.saveReservation(reservation);

        // Add the reservation details to the model for confirmation
        model.addAttribute("date", date);
        model.addAttribute("time", time);
        model.addAttribute("guests", guests);
        model.addAttribute("phone", phone);
        model.addAttribute("message", "Your reservation has been confirmed!");

        return "customer/reservationConfirmation"; // Return the reservation confirmation page
    }
 
}
