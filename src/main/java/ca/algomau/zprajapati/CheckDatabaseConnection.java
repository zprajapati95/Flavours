package ca.algomau.zprajapati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CheckDatabaseConnection {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @EventListener(ContextRefreshedEvent.class)
    public void checkConnection() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result != null && result == 1) {
                System.out.println("Database connection is established!");
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }
}
