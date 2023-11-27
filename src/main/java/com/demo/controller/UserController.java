package com.demo.controller;

import com.demo.entity.User;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRespository userRespository;

    //get all users
    @GetMapping
    public List<User> getAllUsers(){
        return this.userRespository.findAll();
    }

    //get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") long id){
        return this.userRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: [%s]".formatted(id)));
    }

    //create new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return this.userRespository.save(user);
    }

    // Metodo per aggiornare un utente
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable("id") long id) {
        // Trova l'utente esistente
        User existing = this.userRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // Aggiorna solo i campi non nulli forniti nel corpo della richiesta
        if (newUser.getFirstName() != null) {
            existing.setFirstName(newUser.getFirstName());
        }

        if (newUser.getLastName() != null) {
            existing.setLastName(newUser.getLastName());
        }

        if (newUser.getEmail() != null) {
            existing.setEmail(newUser.getEmail());
        }

        // Salva e restituisci la risposta
        return ResponseEntity.ok(this.userRespository.save(existing));
    }


    //delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") long id){
        User exitingUser = this.userRespository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        this.userRespository.delete(exitingUser);
        return ResponseEntity.ok().build();
    }

}
