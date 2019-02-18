package com.example.android.finalproject.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

public class MainActivity
        extends SingleFragmentActivity
        implements MainFragment.OnFragmentInteractionListener
        , ShopItemFragment.OnFragmentInteractionListener{

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return MainFragment.newInstance();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
