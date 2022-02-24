package rb.com.care.purge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rb.com.care.purge.model.Response;
import rb.com.care.purge.model.Users;
import rb.com.care.purge.service.UsersService;
import rb.com.care.purge.util.ResourceNotFoundException;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Users> getAllUsers() {
        return usersService.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity < Users > getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        Users user = usersService.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Users createUser(@Valid @RequestBody Users user) {
        return usersService.saveOrUpdate(user);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long userId,
                                                  @Valid @RequestBody Users userDetails) throws ResourceNotFoundException {
        Users user = usersService.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        user.setUserName(userDetails.getUserName());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        final Users updateUser = usersService.saveOrUpdate(user);
        return ResponseEntity.ok(updateUser);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response registerUser(@Valid @RequestBody Users newUser) {
        Response response = new Response();
        List<Users> users = usersService.findAll();
        System.out.println("New user: " + newUser.toString());
        for (Users user : users) {
            System.out.println("Registered user: " + newUser.toString());
            if (user.equals(newUser)) {
                System.out.println("User Already exists!");
                response.setMessage("User Already exists!");
            }
        }
        usersService.saveOrUpdate(newUser);
        response.setStatus(HttpStatus.OK);
        response.setData(newUser);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response loginUser(@Valid @RequestBody Users user)  {
        Response response = new Response();
        Users userDetails = usersService.findByUserName(user.getUserName()).orElse(null);
        if(!Objects.isNull(userDetails)){
            if(userDetails.getPassword().equals(user.getPassword())){
                response.setData(userDetails);
            } else {
                response.setMessage("You have entered an invalid username or password");
            }
        } else {
            response.setMessage("You have entered an invalid username or password");
        }
        response.setStatus(HttpStatus.OK);
        return response;
    }

}
