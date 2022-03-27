package kr.codesquad.cafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"SqlResolve", "SqlNoDataSourceInspection"})
@Repository
public class UserRepository {

    private static final String SQL_SAVE_USER =
            "INSERT INTO CAFE_USER (userid, password, name, email) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE password=?, name=?, email=?";
    private static final String SQL_FIND_USER_BY =
            "SELECT userid, password, name, email FROM CAFE_USER WHERE %s=?";
    private static final String SQL_FIND_USER_BY_USERID = String.format(SQL_FIND_USER_BY, "userid");
    private static final String SQL_FIND_USER_BY_NAME = String.format(SQL_FIND_USER_BY, "name");
    private static final String SQL_FIND_USER_BY_EMAIL = String.format(SQL_FIND_USER_BY, "email");
    private static final String SQL_FIND_USER_ALL =
            "SELECT userid, password, name, email FROM CAFE_USER";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(User user) {
        jdbcTemplate.update(SQL_SAVE_USER,
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail(),
                user.getPassword(), user.getName(), user.getEmail());
    }

    public Optional<User> findByUserId(String userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_USER_BY_USERID, userRowMapper(), userId));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByName(String name) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_USER_BY_NAME, userRowMapper(), name));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_FIND_USER_BY_EMAIL, userRowMapper(), email));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_USER_ALL, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return ((rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getString("userid"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));

            return user;
        });
    }
}
