package com.rio_rishabhNEU.UserApp.Controllers;

import java.time.LocalDateTime;
import java.util.Optional;

import com.rio_rishabhNEU.UserApp.ExceptionHandlers.EmailNotAvailableException;
import com.rio_rishabhNEU.UserApp.Model.User;
import com.rio_rishabhNEU.UserApp.Service.UserService;
import com.rio_rishabhNEU.UserApp.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/v1/user")
    public ResponseEntity<User> createUser(@RequestBody User user) throws EmailNotAvailableException {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/v1/user/self")
    public ResponseEntity<User> getUser() {
        String email = AuthUtil.getAuthenticatedUserEmail();

        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // User not found
        }
    }

    @PutMapping("/v1/user/self")
    public ResponseEntity<User> updateUser(@RequestBody User userUpdate) {
        String email = AuthUtil.getAuthenticatedUserEmail();

        if (email == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<User> optionalUser = userService.getUserByEmail(email);

        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
        }

        User currentUser = optionalUser.get();

        // Check for invalid fields
        if (userUpdate.getEmail() != null || userUpdate.getId() != null || userUpdate.getAccountCreated() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Invalid update attempt
        }

        // Update the fields allowed
        if (userUpdate.getFirstName() != null) {
            currentUser.setFirstName(userUpdate.getFirstName());
        }

        if (userUpdate.getLastName() != null) {
            currentUser.setLastName(userUpdate.getLastName());
        }

        if (userUpdate.getPassword() != null) {
            String hashedPassword = userService.hashPassword(userUpdate.getPassword());
            currentUser.setPassword(hashedPassword);
        }

        currentUser.setAccountUpdated(LocalDateTime.now());

        // Save the updated user to the database
        userService.updateUser(currentUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204
    }

    // Handle HEAD requests by explicitly returning 405
    @RequestMapping(value = "/v1/user/self", method = RequestMethod.HEAD)
    public ResponseEntity<Void> headRequest() {
        return ResponseEntity.status(405).build();
    }

    // Handle OPTIONS requests by explicitly returning 405
    @RequestMapping(value = "/v1/user/self", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsRequest() {
        return ResponseEntity.status(405).build();
    }
}
