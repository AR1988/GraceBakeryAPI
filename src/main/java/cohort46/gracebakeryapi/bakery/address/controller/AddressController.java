package cohort46.gracebakeryapi.bakery.address.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AddressController {
    /*
    private final AddressService addressService;


    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDto addAddress(@RequestBody AddressDto addressDto) {
        return addressService.addAddress(addressDto)  ;
    }

    @GetMapping("/address/{id}")
    public AddressDto findAddressById(@PathVariable Long id) {
        return addressService.findAddressById(id);
    }

    @DeleteMapping("/address/{id}")
    public AddressDto deleteAddress(@PathVariable Long id) {
        return addressService.deleteAddress(id);
    }

    @PutMapping("/address/{id}")
    public AddressDto updateAddress( @RequestBody AddressDto addressDto, @PathVariable Long id) {
        return addressService.updateAddress(addressDto, id);
    }

    @GetMapping("/addresses/user/{user_id}")
    public Iterable<AddressDto> findAddressesByUserId(@PathVariable Long user_id) {
        return addressService.findAddressesByUserId(user_id);
    }

    @GetMapping("/addresses/including/{including}")
    public Iterable<AddressDto> findAddressesByIncluding(@PathVariable String including) {
        return addressService.findAddressesByIncluding(including);
    }

     */
}
