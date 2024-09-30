package com.bill.services;


import com.bill.dtos.store.CreateStoreDto;
import com.bill.exceptions.EntityNotFoundException;
import com.bill.model.Store;
import com.bill.repositories.StoreEmployeeRepository;
import com.bill.repositories.StoreRepository;
import com.bill.services.user.AuthenticationService;
import com.bill.model.user.User;
import com.bill.model.CustomerType;
import com.bill.model.StoreUser;
import com.bill.dtos.store.StoreResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    private final ModelMapper modelMapper;

    private final AuthenticationService authenticationService;

    private final StoreEmployeeRepository storeEmployeeRepository;

    @Transactional
    public StoreResponseDto createStore(CreateStoreDto createStoredto) {
        Store store = new Store();
        modelMapper.map(createStoredto, store);
        Store savedStore = storeRepository.save(store);
        return modelMapper.map(savedStore, StoreResponseDto.class);
    }

    @Transactional
    public void assignUserAsCustomerToStore(long storeId, CustomerType customerType) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        User user = authenticationService.getCurrentAuthenticatedUser();

        if (previousExist(storeId, user.getId(), customerType)) {
            throw new RuntimeException("already there");
        }

        StoreUser storeEmployee = new StoreUser();
        storeEmployee.setStore(store);
        storeEmployee.setUser(user);
        storeEmployee.setCustomerType(customerType);

        storeEmployeeRepository.save(storeEmployee);
    }

    public boolean dostorehasemployee(long storeId, Integer employeeId) {
        return storeEmployeeRepository.
                findOneByStoreIdAndUserId(storeId, employeeId).isPresent();
    }

    public boolean previousExist(long storeId, Integer employeeId, CustomerType customerType) {
        return storeEmployeeRepository.
                findOneByStoreIdAndUserIdAndCustomerType(storeId, employeeId, customerType).isPresent();
    }

    public Optional<StoreUser> getRegisteration(long storeId, Integer employeeId, CustomerType customerType) {
        return storeEmployeeRepository.
                findOneByStoreIdAndUserIdAndCustomerType(storeId, employeeId, customerType);
    }

    public Store findById(long id) {
        return storeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Store.class, "id", String.valueOf(id)));
    }
}
