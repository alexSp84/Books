package eu.fse.books;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private ArrayList<Book> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTxtV;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTxtV = (TextView)itemView.findViewById(R.id.title_txt_view);
        }

    }

    public BooksAdapter(ArrayList<Book> myDataset) {
        mDataset = myDataset;
    }

    public void addBooksList(ArrayList<Book> books){
        mDataset.addAll(books);
        notifyDataSetChanged();
    }

    public void clearBooksList(){
        mDataset.clear();
        notifyDataSetChanged();
    }

    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(BooksAdapter.ViewHolder holder, int position) {
        holder.titleTxtV.setText(mDataset.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
