package com.example.sd_lab2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragment extends Fragment {

    private ListAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ObjectList objectList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, null);
        objectList = ObjectList.getInstance();

        final MainActivity activity = (MainActivity) getActivity();
        listAdapter = new ListAdapter(objectList, activity);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setItemAnimator(null); //to stop from jumping

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy != 0) {
                    activity.requestGraphicForVisible();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null)
            recyclerView.scrollToPosition(getArguments().getInt("current_item"));
    }

    public ListAdapter getAdapter() {
        return listAdapter;
    }

    public LinearLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }
}
