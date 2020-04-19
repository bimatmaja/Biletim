package com.example.biletim;

import android.content.Intent;

public class MyTicket {

    String name_tour, location, total_ticket;

    public MyTicket() {
    }

    public MyTicket(String name_tour, String location, String total_ticket) {
        this.name_tour = name_tour;
        this.location = location;
        this.total_ticket = total_ticket;
    }

    public String getName_tour() {
        return name_tour;
    }

    public void setName_tour(String name_tour) {
        this.name_tour = name_tour;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotal_ticket() {
        return total_ticket;
    }

    public void setTotal_ticket(String total_ticket) {
        this.total_ticket = total_ticket;
    }
}
