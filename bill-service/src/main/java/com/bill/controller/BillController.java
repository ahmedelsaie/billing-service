package com.bill.controller;

import com.bill.controller.dto.BillRequestDto;
import com.bill.dtos.BillRequest;
import com.bill.dtos.BillResult;
import com.bill.services.BillService;
import com.bill.services.user.AuthenticationService;
import com.bill.model.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class BillController {

    private final AuthenticationService authenticationService;

    private final BillService billService;

    private final ModelMapper modelMapper;

    @PostMapping("/calculate")
    @PreAuthorize("hasAuthority('USER')")
    public BillResult calucalte(@RequestBody @Valid BillRequestDto billRequestDto)
    {
        User currentUser = authenticationService.getCurrentAuthenticatedUser();
        BillRequest billRequest =new BillRequest();
        billRequest.setUserId(currentUser.getId());
        modelMapper.map(billRequestDto,billRequest);
        return billService.calculateBill(billRequest);
    }
}
