package com.example.biletim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTicket> myTicket;

    public TicketAdapter(Context c, ArrayList<MyTicket> p){
        context = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // replacing with the updated data
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_myticket, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.xname_tour.setText(myTicket.get(i).getName_tour());
        myViewHolder.xlocation.setText(myTicket.get(i).getLocation());
        myViewHolder.xtotal_ticket.setText(myTicket.get(i).getTotal_ticket() + " Tickets");

        final String getNameTour = myTicket.get(i).getName_tour();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomyticketdet = new Intent(context, MyTicketDetailAct.class);
                gotomyticketdet.putExtra("name_tour", getNameTour);
                context.startActivity(gotomyticketdet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTicket.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView xname_tour, xlocation, xtotal_ticket;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xname_tour = itemView.findViewById(R.id.xname_tour);
            xlocation = itemView.findViewById(R.id.xlocation);
            xtotal_ticket = itemView.findViewById(R.id.xtotal_ticket);


        }
    }
}
