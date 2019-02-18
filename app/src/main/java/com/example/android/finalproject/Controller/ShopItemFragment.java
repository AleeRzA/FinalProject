package com.example.android.finalproject.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.finalproject.R;
import com.example.android.finalproject.model.Products;
import com.example.android.finalproject.network.APIFetching;
import com.example.android.finalproject.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShopItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShopItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopItemFragment extends Fragment {

    private ImageView mProductImg;
    private TextView mProductTitle, mProductDesc;
    private static final String ARG_PARAM1 = "param1";
    private Products mProduct;
    private int mProductId;
    private OnFragmentInteractionListener mListener;

    public ShopItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ShopItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopItemFragment newInstance(int param1) {
        ShopItemFragment fragment = new ShopItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductId = getArguments().getInt(ARG_PARAM1);
        }
        RetrofitClientInstance
                .getRetrofitInstance()
                .create(APIFetching.class)
                .getProduct(String.valueOf(mProductId))
                .enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                mProduct = response.body();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop_item, container, false);
        mProductTitle = view.findViewById(R.id.product_title);
        mProductDesc = view.findViewById(R.id.product_desc);
        mProductTitle.setText(mProduct.getName());
        mProductDesc.setText(mProduct.getPrice());
        if(mProduct.getImages() != null && mProduct.getImages().size() > 0) {
            Picasso.get().load(mProduct.getImages().get(0).getImgSrc()).into(mProductImg);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
