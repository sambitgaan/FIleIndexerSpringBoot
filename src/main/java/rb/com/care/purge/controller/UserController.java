package rb.com.care.purge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rb.com.care.purge.model.*;
import rb.com.care.purge.service.JwtUserDetailsService;
import rb.com.care.purge.service.UsersService;
import rb.com.care.purge.util.JwtTokenUtil;
import rb.com.care.purge.util.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.*;

import static org.apache.lucene.queries.function.valuesource.LiteralValueSource.hash;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

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
        Users users = usersService.findByUserName(newUser.getUserName()).orElse(null);
        if (!Objects.isNull(users)) {
            System.out.println("User Already exists!");
            response.setMessage("User Already exists!");
        } else {
            usersService.saveOrUpdate(newUser);
            response.setData(newUser);
        }
        response.setStatus(HttpStatus.OK);
        return response;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public LoginResponse loginUser(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {
        LoginResponse response = new LoginResponse();

        Users userDetails = usersService.findByUserName(authenticationRequest.getUserName()).orElse(null);
        final UserDetails userData = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        if(!Objects.isNull(userData)){
            if(bcryptEncoder.matches(authenticationRequest.getPassword(), userData.getPassword())){
                final String token = jwtTokenUtil.generateToken(userData);
                response.setData(userDetails);
                response.setToken(token);
                response.setStatus(HttpStatus.OK);
            } else {
                response.setMessage("You have entered an invalid username or password");
                response.setStatus(HttpStatus.OK);
            }
        } else {
            response.setMessage("You have entered an invalid username or password");
        }
        return response;
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
