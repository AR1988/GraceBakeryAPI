package cohort46.gracebakeryapi.bakery.address.dao;

import cohort46.gracebakeryapi.bakery.address.model.Address;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AddressRepository extends JpaRepository<Address, Long> {
    //Set<Address> findAllByAddressContainingIgnoreCaseAndCityContainingIgnoreCaseAndStreetContainingIgnoreCase(String street, Sort sort);
    //Set<Address> findAllByAddressContainingIgnoreCaseOrCityContainingIgnoreCaseOrStreetContainingIgnoreCase(String address, String city, String street);//, Sort sort);
    Set<Address> findAllByUserId(Long userId);
}