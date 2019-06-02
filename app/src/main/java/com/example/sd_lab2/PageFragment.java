package com.example.sd_lab2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class PageFragment extends Fragment {
    private int currentNumber;

    public static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("current_number", page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        currentNumber = getArguments().getInt("current_number");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_page, null);

        ImageView imageView = view.findViewById(R.id.tech_image);
        TextView nameTextView = view.findViewById(R.id.tech_name);
        TextView helptextTextView = view.findViewById(R.id.tech_helptext);

        ObjectList objectList = ObjectList.getInstance();

        ObjectList.Data data = objectList.getData(currentNumber);

        nameTextView.setText(data.name);
        helptextTextView.setText(data.helptext);

        if (data.graphicBitmap == null) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(data.graphicBitmap);
        }

        return view;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }
}