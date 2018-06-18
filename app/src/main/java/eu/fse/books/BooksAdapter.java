package eu.fse.books;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private ArrayList<Book> mDataset;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTxtV;
        public TextView authorsTxtV;
        public ImageView thumbnailImgV;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            card = (CardView)itemView.findViewById(R.id.cv);
            titleTxtV = (TextView)itemView.findViewById(R.id.title_txt_view);
            authorsTxtV = (TextView) itemView.findViewById(R.id.authors_txt_view);
            thumbnailImgV = (ImageView)itemView.findViewById(R.id.thumbnail);
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

        String authorsList = "";
        int sizeAuthorsList = mDataset.get(position).getAuthors().size();

        holder.titleTxtV.setText(mDataset.get(position).getTitle());

        for(int i = 0; i < sizeAuthorsList; i++){
            authorsList+= mDataset.get(position).getAuthors().get(i);
            if(i<sizeAuthorsList-1)
                authorsList+=", ";
        }

        holder.authorsTxtV.setText(authorsList);

        Glide.with(holder.thumbnailImgV.getContext())
                .load(mDataset.get(position).getThumbnail())
                .into(holder.thumbnailImgV);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
