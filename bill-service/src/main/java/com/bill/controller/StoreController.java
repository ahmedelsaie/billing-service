package com.bill.controller;

import com.bill.repositories.StoreRepository;
import com.bill.dtos.store.CreateStoreDto;
import com.bill.model.user.User;
import com.bill.dtos.store.StoreResponseDto;
import com.bill.services.StoreService;
import com.bill.services.user.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/stores")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class StoreController {

    private final StoreRepository storeRepository;

    private final AuthenticationService authenticationService;

    private final StoreService storeService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public StoreResponseDto createStore(@RequestBody @Valid CreateStoreDto createStoreDto) {
        System.out.println("inidse first controller");
        User currentUser = authenticationService.getCurrentAuthenticatedUser();
        createStoreDto.setUserId(currentUser.getId());
        System.out.println("inidse first controller 2" );

        var x= storeService.createStore(createStoreDto);
        System.out.println("inidse first controller 3" );

        System.out.println("the x is " + x);
        return x;
    }

}
