package com.example.sd_lab2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PagerFragment extends Fragment {
    private PagerAdapter pagerAdapter;
    private ViewPager pager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        pager = view.findViewById(R.id.pager);

        pagerAdapter = new PagerAdapter(getChildFragmentManager());

        pager.setAdapter(pagerAdapter);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                MainActivity activity = (MainActivity) getActivity();

                if(activity != null)
                    activity.requestGraphic(ObjectList.getInstance().getData(position), position);

                activity.updateListFragmentCurrentItem(position);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null)
            pager.setCurrentItem(getArguments().getInt("current_item"));
    }

    public PagerAdapter getAdapter() {
        return pagerAdapter;
    }
}
