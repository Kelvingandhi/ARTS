package com.example.soham.seatBookingrecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.soham.arts.R;
import com.example.soham.arts.Ticket_Genrate;

import java.util.List;

public class AirplaneAdapter extends SelectableAdapter<RecyclerView.ViewHolder> {

    private OnSeatSelected mOnSeatSelected;

    int flag=0;
    int newflag=0;

    private static class EdgeViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSeat;
        private final ImageView imgSeatSelected;

        private final ImageView imgSeatBooked;


        public EdgeViewHolder(View itemView) {
            super(itemView);
            imgSeat = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            imgSeatBooked = (ImageView) itemView.findViewById(R.id.img_seat_booked);

        }

    }
/*
    private static class CenterViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSeat;
        private final ImageView imgSeatSelected;
        private final ImageView imgSeatBooked;

        public CenterViewHolder(View itemView) {
            super(itemView);
            imgSeat = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);

            imgSeatBooked = (ImageView) itemView.findViewById(R.id.img_seat_booked);


        }

    }
*/

    private static class BookedViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSeat;
        private final ImageView imgSeatSelected;

        private final ImageView imgSeatBooked;


        public BookedViewHolder(View itemView) {
            super(itemView);
            imgSeat = (ImageView) itemView.findViewById(R.id.img_seat);
            imgSeatSelected = (ImageView) itemView.findViewById(R.id.img_seat_selected);
            imgSeatBooked = (ImageView) itemView.findViewById(R.id.img_seat_booked);

        }

    }

/*
    private static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }

    }
*/
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<AbstractItem> mItems;
   // private  List<AbstractItem> mItems1;

    public AirplaneAdapter(Context context, List<AbstractItem> items) {
        mOnSeatSelected = (OnSeatSelected) context;
       // mOnSeatBooked = (OnSeatBooked) context;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
       // mItems1 = items1;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*if (viewType == AbstractItem.TYPE_CENTER) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new CenterViewHolder(itemView);
        } else*/ if (viewType == AbstractItem.TYPE_EDGE) {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new EdgeViewHolder(itemView);
        }
        else
        //if (viewType == AbstractItem.TYPE_BOOKED)
        {
            View itemView = mLayoutInflater.inflate(R.layout.list_item_seat, parent, false);
            return new BookedViewHolder(itemView);
        }  /*else {
                View itemView = new View(mContext);
                return new EmptyViewHolder(itemView);
        }*/
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        int type = mItems.get(position).getType();
        /*if (type == AbstractItem.TYPE_CENTER) {
            final CenterItem item = (CenterItem) mItems.get(position);
            CenterViewHolder holder = (CenterViewHolder) viewHolder;


            if(getSelectedItemCount()+1<=Integer.parseInt(Ticket_Genrate.psgr.getSelectedItem().toString()))
            {
                holder.imgSeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        toggleSelection(position);

                        //mOnSeatSelected.onSeatSelected(getSelectedItemCount());

                        mOnSeatSelected.onSeatSelected(getnewSelectedItems());


                    }
                });

                holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
            }
            else
            {
                //Toast.makeText(.this, "Not Allowed", 0).show();
            }


            //holder.imgSeatBooked.setVisibility(?View.VISIBLE:View.INVISIBLE);


        } else*/ if (type == AbstractItem.TYPE_EDGE) {
            final EdgeItem item = (EdgeItem) mItems.get(position);
            EdgeViewHolder holder = (EdgeViewHolder) viewHolder;




                holder.imgSeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(getSelectedItemCount()<Integer.parseInt(Ticket_Genrate.psgr.getSelectedItem().toString())) {

                                if (flag == 0) {
                                    toggleSelection(position);
                                    flag++;
                                } else {
                                    if (isSelected(position)) {
                                        toggleSelection(position);
                                        flag--;
                                        newflag++;

                                    } else {
                                        toggleSelection(position);
                                        flag++;
                                    }
                                }



                            //mOnSeatSelected.onSeatSelected(getSelectedItemCount());

                            mOnSeatSelected.onSeatSelected(getnewSelectedItems());
                            //flag=getSelectedItemCount();
                        }
                        else
                        {
                            //if(flag==Integer.parseInt(Ticket_Genrate.psgr.getSelectedItem().toString())) {
                                if (isSelected(position)) {
                                    toggleSelection(position);
                                    //flag--;
                                }
                            //}
                        }


                    }
                });

                holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

            /*else
            {
                holder.imgSeatSelected.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);

            }*/

        }

        else if (type == AbstractItem.TYPE_BOOKED) {
            final BookedItem item = (BookedItem) mItems.get(position);
            BookedViewHolder holder = (BookedViewHolder) viewHolder;


            //holder.imgSeat.setVisibility(View.VISIBLE);

            holder.imgSeatBooked.setVisibility(View.VISIBLE);

        }

    }

}
