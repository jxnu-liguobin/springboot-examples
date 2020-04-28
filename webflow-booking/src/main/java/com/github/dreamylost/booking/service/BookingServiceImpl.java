package com.github.dreamylost.booking.service;

import com.github.dreamylost.booking.mode.Booking;
import com.github.dreamylost.booking.mode.Hotel;
import com.github.dreamylost.booking.mode.SearchCriteria;
import com.github.dreamylost.booking.mode.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * jpa 服务层
 */
@Service("bookingService")
@Repository
public class BookingServiceImpl implements BookingService {

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Booking> findBookings(String username) {
        if (username != null) {
            return em.createQuery("select b from Booking b where b.user.username = :username order by b.checkinDate")
                    .setParameter("username", username).getResultList();
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Hotel> findHotels(SearchCriteria criteria) {
        String pattern = getSearchPattern(criteria);
        int startIndex = criteria.getPage() * criteria.getPageSize();
        return em
                .createQuery(
                        "select h from Hotel h where lower(h.name) like :pattern or lower(h.city) like :pattern "
                                + " or lower(h.zip) like :pattern or lower(h.address) like :pattern")
                .setParameter("pattern", pattern).setFirstResult(startIndex).setMaxResults(criteria.getPageSize())
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Hotel findHotelById(Long id) {
        return em.find(Hotel.class, id);
    }

    @Transactional(readOnly = true)
    public Booking createBooking(Long hotelId, String username) {
        Hotel hotel = em.find(Hotel.class, hotelId);
        User user = findUser(username);
        Booking booking = new Booking(hotel, user);
        return booking;
    }

    @Transactional
    public void persistBooking(Booking booking) {
        em.persist(booking);
    }

    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = em.find(Booking.class, id);
        if (booking != null) {
            em.remove(booking);
        }
    }

    private String getSearchPattern(SearchCriteria criteria) {
        if (StringUtils.hasText(criteria.getSearchString())) {
            return "%" + criteria.getSearchString().toLowerCase().replace('*', '%') + "%";
        } else {
            return "%";
        }
    }

    private User findUser(String username) {
        return (User) em.createQuery("select u from User u where u.username = :username")
                .setParameter("username", username).getSingleResult();
    }

}