import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyDatabaseUserDetailsService implements UserDetailsService {


    @Autowired
    public MyDatabaseUserDetailsService(Database database) {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user details from your database
        User user = Database.searchUser(username)

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Create a UserDetails object based on your user details
        UserDetails userDetails = User.builder()
                .username(user.getUsername())
                .password(user.getPassword()) 
                .roles("USER") 
                .build();

        return userDetails;
    }
}
