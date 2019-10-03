package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeController {

    @Autowired
    FlightRepository flightRepository;

    @RequestMapping("/")
    public String listFlights(Model model) {
        model.addAttribute("flights", flightRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String addFlight(Model model) {
        model.addAttribute("flight", new Flight());
        return "flightform";
    }

    @PostMapping("/processFlight")
    public String processFlight(@ModelAttribute Flight flight, @RequestParam(name = "date") String date, @RequestParam(name = "price") String price) {
//        Handling the price
        Double doublePrice = Double.valueOf(price);
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String realPrice = currency.format(doublePrice);
        flight.setPrice(realPrice);

//         Handling the date
        String pattern = "yyyy-MM-dd HH:mm";
        try {
            String formattedDate = date.substring(1);
            formattedDate = formattedDate.replace("T", " ");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date realDate = simpleDateFormat.parse(formattedDate);
            flight.setDate(realDate);
        }

        catch (java.text.ParseException e){
            e.printStackTrace();
        }
        flightRepository.save(flight);
        return "redirect:/";
    }

    @PostMapping("/processSearch")
    public String processSearch(Model model, @RequestParam(name = "category") String category, @RequestParam(name = "search") String search) {
        if (category.equalsIgnoreCase("1")) {
            model.addAttribute("arrivalFlights", flightRepository.findByArrivesAtContainingIgnoreCase(search));
        }
        else if (category.equalsIgnoreCase("2")) {
            model.addAttribute("departFlights", flightRepository.findByDepartsFromContainingIgnoreCase(search));
        }
        return "searchList";
    }


}
