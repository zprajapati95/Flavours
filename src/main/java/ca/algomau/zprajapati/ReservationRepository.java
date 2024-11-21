package ca.algomau.zprajapati;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Autowired
    public ReservationRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void saveReservation(Reservation reservation) {
        String phoneNumber = reservation.getPhoneNumber();
        if (phoneNumber.length() > 10) {
            phoneNumber = phoneNumber.substring(0, 10);  // Truncate to 15 characters
        }

        String query = "INSERT INTO reservations (reservation_date, reservation_time, number_of_guests, phone_number) "
                     + "VALUES (:reservation_date, :reservation_time, :number_of_guests, :phone_number)";
        
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("reservation_date", reservation.getReservationDate());
        parameters.addValue("reservation_time", reservation.getReservationTime());
        parameters.addValue("number_of_guests", reservation.getNumberOfGuests());
        parameters.addValue("phone_number", phoneNumber);
        
        jdbc.update(query, parameters);
        System.out.println("Reservation saved: " + reservation);
    }
    // Method to fetch all reservations from the database
    public List<Reservation> getAllReservations() {
        String query = "SELECT * FROM reservations";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        return jdbc.query(query, parameters, new BeanPropertyRowMapper<>(Reservation.class));
    }

   

}
