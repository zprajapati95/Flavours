package ca.algomau.zprajapati;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DishRowMapper implements RowMapper<Dish> {

    @Override
    public Dish mapRow(ResultSet rs, int rowNum) throws SQLException {
        Dish dish = new Dish();
        dish.setId(rs.getLong("id"));
        dish.setName(rs.getString("name"));
        dish.setDescription(rs.getString("description"));
        dish.setPrice(rs.getDouble("price"));
        dish.setCategory(rs.getString("category")); // Map the category column
        return dish;
    }
}
