package com.bill.repositories;

import com.bill.model.CustomerType;
import com.bill.model.StoreUser;
import jakarta.persistence.Id;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StoreEmployeeRepository extends CrudRepository<StoreUser, Id> {

    Optional<StoreUser> findOneByStoreIdAndUserId(long storeId, Integer userId);

    Optional<StoreUser> findOneByStoreIdAndUserIdAndCustomerType
            (long storeId, Integer userId, CustomerType customerType);

}
