package com.example.sd_lab2;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (!(object instanceof PageFragment))
            return super.getItemPosition(object);

        PageFragment pageFragment = (PageFragment) object;

        ObjectList.Data data = ObjectList.getInstance().getData(pageFragment.getCurrentNumber());

        if (data.graphicBitmap != null) {
            return POSITION_NONE;
        }

        return POSITION_UNCHANGED;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        try{
            super.restoreState(state, loader);
        }catch (NullPointerException | IllegalStateException e){
            // null caught
        }
    }

    @Override
    public int getCount() {
        ObjectList objectList = ObjectList.getInstance();

        return objectList.size();
    }
}
