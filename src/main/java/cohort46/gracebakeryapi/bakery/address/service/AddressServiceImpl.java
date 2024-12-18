package cohort46.gracebakeryapi.bakery.address.service;

//import cohort46.gracebakeryapi.accounting.dao.UserRepository;
import cohort46.gracebakeryapi.accounting.model.User;
import cohort46.gracebakeryapi.bakery.address.controller.AddressController;
import cohort46.gracebakeryapi.bakery.address.dao.AddressRepository;
import cohort46.gracebakeryapi.bakery.address.dto.AddressDto;
import cohort46.gracebakeryapi.bakery.address.model.Address;
import cohort46.gracebakeryapi.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    //private final UserRepository userRepository;
    private AddressController addressController;

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public AddressDto addAddress(AddressDto addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);
        //User user = userRepository.findById(addressDto.getUserid()).orElseThrow(EntityNotFoundException::new);
        //address.setUser(user);
        address.setId(null);
        address = addressRepository.save(address);
        if(address != null) {
            return modelMapper.map(address, AddressDto.class);
        }
        return null;
    }

    @Override
    public AddressDto findAddressById(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(address, AddressDto.class);
    }

    @Transactional
    @Override
    public AddressDto deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(EntityNotFoundException::new);
        addressRepository.delete(address);
        return modelMapper.map(address, AddressDto.class);
    }

    @Transactional
    @Override
    public AddressDto updateAddress(AddressDto addressDto, Long id) {
        addressDto.setId(id);
        Address address = addressRepository.findById(addressDto.getId()).orElseThrow(EntityNotFoundException::new);
        modelMapper.map(addressDto, address);
        return modelMapper.map(addressRepository.save(address), AddressDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<AddressDto> findAddressesByUserId(Long userid) {
        return addressRepository.findAllByUserId(userid).stream().map(a -> modelMapper.map(a, AddressDto.class)).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<AddressDto> findAddressesByIncluding(String including) {
        return null;//addressRepository.findAllByAddressContainingIgnoreCaseOrCityContainingIgnoreCaseOrStreetContainingIgnoreCase
                //(including, Sort.by("id")).stream().map(a -> modelMapper.map(a, AddressDto.class)).toList();
    }

}