package com.deva.service;

import com.deva.enums.AirlineStatus;
import com.deva.payload.request.AirlineRequest;
import com.deva.payload.response.AirlineDropdownItem;
import com.deva.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId);
    AirlineResponse updateAirline(Long ownerId, AirlineRequest airlineRequest) throws Exception;
    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;
    AirlineResponse getAirlineById(Long airlineId) throws Exception;
    Page<AirlineResponse> getAllAirlines(Pageable pageable);
    void deleteAirline(Long airlineId,  Long ownerId);

    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus airlineStatus);
    List<AirlineDropdownItem> getAirlineDropdown();


}
