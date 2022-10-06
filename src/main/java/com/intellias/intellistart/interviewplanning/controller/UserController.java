package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController controller
 */
@RestController
public class UserController {
  private final UserRepository userRepository;

  UserController(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
  List<User> all() {
    return userRepository.findAll();
  }

  @PostMapping("/users")
  User newEmployee(@RequestBody User newUser) {
    return userRepository.save(newUser);
  }

  @GetMapping("/users/{id}")
  User findOne(@PathVariable Long id) {

    // TODO Create custom exception
    return userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(id.toString()));
  }

  // TODO PutMapping for UserController

  @DeleteMapping("/users/{id}")
  void deleteEmployee(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
