package com.example.android.finalproject.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.finalproject.R;
import com.example.android.finalproject.model.Products;
import com.example.android.finalproject.network.APIFetching;
import com.example.android.finalproject.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView mRecentText;
    private TextView mPopularText;
    private TextView mRecomendedText;
    private RecyclerView mRecyclerViewRecent;
    private RecyclerView mRecyclerViewPopular;
    private RecyclerView mRecyclerViewRecommended;

    private List<Products> mProductsRecent = new ArrayList<>();
    private List<Products> mProductsPopular = new ArrayList<>();
    private List<Products> mProductsRecommended = new ArrayList<>();

    private ImageView mImageView;
    private ProgressDialog mProgressDialog;
    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Please Wait");
        mProgressDialog.show();

        APIFetching apiFetching = RetrofitClientInstance
                .getRetrofitInstance()
                .create(APIFetching.class);

               apiFetching.getRecentProducts()
                .enqueue(new Callback<List<Products>>() {
                    @Override
                    public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                        if(response.isSuccessful()){
                            mProductsRecent = response.body();
                            mProgressDialog.cancel();
                            setupAdapter();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Products>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
               apiFetching.getRecentProducts()
                       .enqueue(new Callback<List<Products>>() {
                           @Override
                           public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                               mProductsRecent = response.body();
                               mProductsRecommended = response.body();
                               mProgressDialog.cancel();
                               setupAdapter();
                           }

                           @Override
                           public void onFailure(Call<List<Products>> call, Throwable t) {

                           }
                       });
               apiFetching.getTopSellers().enqueue(new Callback<List<Products>>() {
                   @Override
                   public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                       mProductsPopular = response.body();
                       setupAdapter();
                   }

                   @Override
                   public void onFailure(Call<List<Products>> call, Throwable t) {
                       Toast.makeText(getActivity(), "Something popular went wrong", Toast.LENGTH_SHORT).show();
                   }
               });
    }
    private void setupAdapter() {
        if (isAdded())
            mRecyclerViewRecent.setAdapter(new shopItemAdapter(mProductsRecent));
            mRecyclerViewPopular.setAdapter(new shopItemAdapter(mProductsPopular));
            mRecyclerViewRecommended.setAdapter(new shopItemAdapter(mProductsRecommended));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecentText = view.findViewById(R.id.recents_txt);
        mPopularText = view.findViewById(R.id.popular_txt);
        mRecomendedText = view.findViewById(R.id.recomended_txt);
        mRecyclerViewRecent = view.findViewById(R.id.recents_recView);
        mRecyclerViewRecent.setLayoutManager(new LinearLayoutManager(getActivity()
                                        ,LinearLayoutManager.HORIZONTAL
                                        , false));
        mRecyclerViewPopular = view.findViewById(R.id.popular_recView);
        mRecyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity()
                                                , LinearLayoutManager.HORIZONTAL
                                                , false));
        mRecyclerViewRecommended = view.findViewById(R.id.recomended_recView);
        mRecyclerViewRecommended.setLayoutManager(new LinearLayoutManager(getActivity()
                , LinearLayoutManager.HORIZONTAL
                , false));

        setupAdapter();
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class shopItemViewHolder extends RecyclerView.ViewHolder{

        private CardView mCardView;
        private TextView mProductName, mProductPrice;
        private Products mShopItem;

        public shopItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_view);
            mImageView = itemView.findViewById(R.id.thumbnail);
            mProductName = itemView.findViewById(R.id.item_title);
            mProductPrice = itemView.findViewById(R.id.item_price);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), mProductName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        public void bind(Products products){
            mShopItem = products;
            mCardView.setCardBackgroundColor(getResources().getColor(android.R.color.background_light));
            mProductName.setText(products.getName());
            mProductPrice.setText(products.getPrice() != null ? products.getPrice() : "Not available price");

            if(mShopItem.getImages()!=null&& mShopItem.getImages().size()>0){
                Picasso.get().load(mShopItem.getImages().get(0).getImgSrc()).into(mImageView);

            }
        }
    }
    private class shopItemAdapter extends RecyclerView.Adapter<shopItemViewHolder>{

        private List<Products> mProductsList;

        public shopItemAdapter(List<Products> products) {
            mProductsList = products;
        }

        @NonNull
        @Override
        public shopItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater
                    .from(getActivity())
                    .inflate(R.layout.shop_list_item,viewGroup, false);
            return new shopItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull shopItemViewHolder shopItemViewHolder, int i) {
            Products shopItems = mProductsList.get(i);
            shopItemViewHolder.bind(shopItems);
        }


        @Override
        public int getItemCount() {
            return mProductsList.size();
        }

        @Override
        public int getItemViewType(int position) {
            switch (position){

            }
            return super.getItemViewType(position);
        }
    }
//    private class ShopTask extends AsyncTask<Void, Void, List<Products>> {
//
//        @Override
//        protected List<Products> doInBackground(Void... voids) {
//            try {
////                List<Products> galleryItems = new APIFetcher().fetchItems();
//                return galleryItems;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override
//        protected void onPostExecute(List<Products> galleryItems) {
//            super.onPostExecute(galleryItems);
//            mProductsList = galleryItems;
//            setupAdapter();
//        }
//    }

}
