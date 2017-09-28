package com.example.soham.seatBookingrecyclerView;

public class BookedItem extends AbstractItem {

    public BookedItem(String label) {
        super(label);
    }



    @Override
    public int getType() {
        return TYPE_BOOKED;
    }

}
