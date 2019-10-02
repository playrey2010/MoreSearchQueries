package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Date;

public interface FlightRepository extends CrudRepository<Flight, Long> {
    ArrayList<Flight> findByArrivesAtContainingIgnoreCase(String arrrivesSearch);
    ArrayList<Flight> findByDepartsFromContainingIgnoreCase(String dateSearch);

}
