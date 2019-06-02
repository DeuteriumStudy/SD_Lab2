package com.example.sd_lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    private ListFragment listFragment;
    private PagerFragment pagerFragment;

    public static final String TAG = "MainTag";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        listFragment = new ListFragment();
        pagerFragment = new PagerFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_placeholder, listFragment)
                .commit();

    }

    public void updateListFragmentCurrentItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("current_item", position);
        listFragment.setArguments(bundle);
    }

    public void openViewPager(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("current_item", position);

        pagerFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder, pagerFragment)
                .addToBackStack(null)
                .commit();
    }

    public void requestGraphicForVisible() {
        LinearLayoutManager layoutManager = listFragment.getLinearLayoutManager();

        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        Log.d("What", firstVisibleItemPosition + " " + lastVisibleItemPosition);

        ObjectList objectList = ObjectList.getInstance();

        for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
            ObjectList.Data data = objectList.getData(i);

            if (data.graphicBitmap == null && !data.requested) {
                requestGraphic(data, i);
            }
        }
    }


    public void requestGraphic(final ObjectList.Data data, final int position){
        String url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/master/src/images/tech/";

        final ListAdapter adapter1 = listFragment.getAdapter();
        final PagerAdapter adapter2 = pagerFragment.getAdapter();

        ImageRequest request = new ImageRequest(
                url.concat(data.graphic),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        data.graphicBitmap = response;

                        if (adapter1 != null)
                            adapter1.notifyItemChanged(position);
                        if (adapter2 != null)
                            adapter2.notifyDataSetChanged();
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        data.graphicBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.warning);
                        if (adapter1 != null)
                            adapter1.notifyItemChanged(position);
                        if (adapter2 != null)
                            adapter2.notifyDataSetChanged();
                    }
                });
        request.setTag(TAG);

        requestQueue.add(request);

        data.requested = true;
    }

    @Override
    protected void onStop () {
        super.onStop();

        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

}
