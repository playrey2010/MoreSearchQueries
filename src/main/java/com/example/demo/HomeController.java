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

    @RequestMapping("/base")
    public String base() {
        return "base";
    }

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
        System.out.println(date.concat(" - this is before doing anything"));
        if (price.contains("$")) {
            price = price.substring(1, price.length());
        }
        Double doublePrice = Double.valueOf(price);
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        String realPrice = currency.format(doublePrice);
        flight.setPrice(realPrice);

//         Handling the date
        String pattern = "yyyy-MM-dd HH:mm";
        String formattedDate = "";

        boolean inside = false;
        try {
            for (Flight flightX : flightRepository.findAll()) {
                if (flightX.getId() == flight.getId()) {
                    inside = true;
                    break;
                }

            }
            if (inside) {
                System.out.println(date + "- This is the date inside");
                date = date.substring(18, date.length());
                System.out.println(date);
                if (date.contains("T")) {
//                    pattern = "yyyy-MMM-dd HH:mm";
                    formattedDate = date.replace("T", " ");

                    System.out.println(formattedDate.concat( " - after removing the \"T\""));
                }

//
//                pattern = "yyyy-MMM-dd HH:mm";
//                formattedDate = date.substring(0, date.length()-1);
//                System.out.println(formattedDate.concat(" - this is the updated version"));
            } else {
                formattedDate = date.substring(1);
                System.out.println(formattedDate.concat(" - after you take out the 1st index"));
                formattedDate = formattedDate.replace("T", " ");
                System.out.println(formattedDate.concat(" - after removing the \"T\""));
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date realDate = simpleDateFormat.parse(formattedDate);
            System.out.println(realDate + "- this is realDate");
            flight.setDate(realDate);
        }

        catch (java.text.ParseException e){
            e.printStackTrace();
        }
        flightRepository.save(flight);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("flight", flightRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateFlight(@PathVariable("id") long id, Model model) {
        model.addAttribute("flight", flightRepository.findById(id).get());
        return "flightform";
    }

    @PostMapping()

    @RequestMapping("/delete/{id}")
    public String delFlight(@PathVariable("id") long id) {
        flightRepository.deleteById(id);
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
