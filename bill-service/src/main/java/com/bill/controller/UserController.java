package com.bill.controller;

import com.bill.model.user.User;
import com.bill.model.CustomerType;
import com.bill.services.StoreService;
import com.bill.services.user.AuthenticationService;
import com.bill.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {
    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final StoreService storeService;

    @GetMapping("/me")
    public User authenticatedUser() {
        return authenticationService.getCurrentAuthenticatedUser();
    }

    @GetMapping
    public List<User> allUsers() {
        return userService.allUsers();
    }

    @PostMapping("/makeEmployeeToStore")
    public void makeEmployee(@RequestBody @NotEmpty Long storeId) {
        storeService.assignUserAsCustomerToStore(storeId, CustomerType.EMPLOYEE);
    }

    @PostMapping("/makeAffiliateToStore")
    public void makeAffiliate(@RequestBody @NotEmpty Long storeId) {
        storeService.assignUserAsCustomerToStore(storeId, CustomerType.AFFILIATE);
    }

    @PostMapping("/makeCustomerToStore")
    public void makeCustomer(@RequestBody @NotEmpty Long storeId) {
        storeService.assignUserAsCustomerToStore(storeId, CustomerType.CUSTOMER);
    }
}
