package com.example.dbtodb.config.steps.reader;

import com.example.dbtodb.model.User;
import com.example.dbtodb.repository.UserRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Component
public class UserReader extends RepositoryItemReader<User> implements ItemReader<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserReader(UserRepository userRepository){
        this.userRepository = userRepository;
        setRepository(userRepository);
        setMethodName("findAll");
        final HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        setSort(sorts);
    }

    public class UserRowMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getNString("name"));
            user.setEmail(rs.getNString("email"));
            System.out.println(user.getName());
            return user;
        }
    }
}
