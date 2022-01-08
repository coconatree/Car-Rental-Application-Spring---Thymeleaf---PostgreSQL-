package com.car_rental.Impl.RepositoryImpl;

import com.car_rental.Model.CompositeModel.CarAndReviewAmount;
import com.car_rental.Model.DatabaseModel.Car;
import com.car_rental.Interface.RepositoryI.CarRepoI;
import com.car_rental.Model.PrimitiveRowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

@Repository
public class CarRepoImpl implements CarRepoI {

    private JdbcTemplate template;

    @Autowired
    public CarRepoImpl(JdbcTemplate template) { this.template = template; }

    @Override
    public Car Select(Integer id) throws DataAccessException, SQLException {
        return this.template.queryForObject(
                String.format("SELECT * FROM car AS c WHERE c.car_id = %s", id), Car.GetMapper());
    }

    @Override
    public Car Insert(Car data) throws DataAccessException {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO car (brand, model, gear_type, price, manager_id, price_per_day) VALUES (?, ?, ?, ?, ?, ?);", new String[]{"car_id"});
            ps.setString(1, data.getBrand());
            ps.setString(2, data.getModel());
            ps.setString(3, data.getGearType());
            ps.setDouble(4, data.getPrice());
            ps.setInt(5, data.getManagerInteger());
            ps.setDouble(6, data.getPricePerDay());
            return ps;
        }, keyHolder);

        Integer insertedId = (Integer) keyHolder.getKey();
        data.setId(insertedId);

        return data;
    }

    @Override
    public List<String> SelectAllModel() throws DataAccessException {
        return this.template.query("SELECT DISTINCT c.model FROM car AS c;", PrimitiveRowMappers.GetStringRowMapper());
    }

    @Override
    public List<String> SelectAllBrand() throws DataAccessException {
        return this.template.query("SELECT DISTINCT c.brand FROM car AS c;", PrimitiveRowMappers.GetStringRowMapper());
    }

    @Override
    public List<String> SelectAllGearTypes() throws DataAccessException {
        return this.template.query("SELECT DISTINCT c.gear_type FROM car AS c;", PrimitiveRowMappers.GetStringRowMapper());
    }

    @Override
    public List<CarAndReviewAmount> SelectFiltered(List<String> models, List<String> brands, List<String> gearTypes) {
        StringBuilder sb = new StringBuilder();
        boolean hasWhere = false;

        sb.append("SELECT c.car_id,  c.brand, c.model, c.gear_type, c.price, c.manager_id, ")
                .append("(SELECT count(review_id) AS amount_of_review FROM review LEFT JOIN car_reservation cr on review.reservation_id = cr.reservation_id ")
                .append("WHERE cr.car_id = c.car_id)")
                .append("FROM car_branch AS cb LEFT JOIN car AS c on cb.car_id = c.car_id ");

        if (!models.isEmpty()) {

            if (!hasWhere) {
                sb.append("WHERE ");
                hasWhere = true;
            }
            else {
                sb.append("AND ");
            }

            sb = this.AddListToStringBuilder(sb, "c.model IN (", models);
        }

        if (!brands.isEmpty()) {

            if (!hasWhere) {
                sb.append("WHERE ");
                hasWhere = true;
            }
            else {
                sb.append("AND ");
            }

            sb = this.AddListToStringBuilder(sb, "c.brand IN (", brands);
        }
        if (!gearTypes.isEmpty()) {

            if (!hasWhere) {
                sb.append("WHERE ");
                hasWhere = true;
            }
            else {
                sb.append("AND ");
            }

            sb = this.AddListToStringBuilder(sb, "c.gear_type IN (", gearTypes);
        }

        sb.append(";");

        final String QUERY = sb.toString();

        return this.template.query(QUERY, Car.GetCarWithReviewMapper());
    }

    @Override
    public List<CarAndReviewAmount> SelectFilteredWithBranchId(List<String> models, List<String> brands, List<String> gearTypes, Integer branchId) {

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT c.car_id,  c.brand, c.model, c.gear_type, c.price, c.manager_id, ")
                .append("(SELECT count(review_id) AS amount_of_review FROM review LEFT JOIN car_reservation cr on review.reservation_id = cr.reservation_id ")
                .append("WHERE cr.car_id = c.car_id)")
                .append("FROM car_branch AS cb LEFT JOIN car AS c on cb.car_id = c.car_id ")
                .append("WHERE branch_id = ").append(branchId).append(" ");

        if (!models.isEmpty())
            sb = this.AddListToStringBuilder(sb, "AND c.model IN (", models);

        if (!brands.isEmpty())
            sb = this.AddListToStringBuilder(sb, "AND c.brand IN (", brands);

        if (!gearTypes.isEmpty())
            sb = this.AddListToStringBuilder(sb, "AND c.gear_type IN (", gearTypes);

        sb.append(";");

        final String QUERY = sb.toString();

        return this.template.query(QUERY, Car.GetCarWithReviewMapper());
    }

    private StringBuilder AddListToStringBuilder(StringBuilder sb, String initial, List<String> list) {

        sb.append(initial);
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                sb.append("'").append(list.get(i)).append("'").append(", ");
            } else {
                sb.append("'").append(list.get(i)).append("'").append(") ");
            }
        }
        return sb;
    }
}
