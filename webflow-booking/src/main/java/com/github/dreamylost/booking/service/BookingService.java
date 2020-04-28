package com.github.dreamylost.booking.service;

import com.github.dreamylost.booking.mode.Booking;
import com.github.dreamylost.booking.mode.Hotel;
import com.github.dreamylost.booking.mode.SearchCriteria;

import java.util.List;

/**
 * 服务层接口
 */
public interface BookingService {

    List<Booking> findBookings(String username);

    List<Hotel> findHotels(SearchCriteria criteria);

    Hotel findHotelById(Long id);

    Booking createBooking(Long hotelId, String userName);

    void persistBooking(Booking booking);

    void cancelBooking(Long id);

}
