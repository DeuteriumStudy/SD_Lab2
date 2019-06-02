package com.example.sd_lab2;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ObjectList {
    private final ArrayList<Data> storage;
    private static final ObjectList list = new ObjectList();


    public final static class Data {
        public final String name;
        public final String helptext;
        public final String graphic;
        public Bitmap graphicBitmap;
        public boolean requested;

        public Data(String name, String helptext, String graphic) {
            this.name = name;
            this.graphic = graphic;
            this.helptext = helptext;
            graphicBitmap = null;
            requested = false;
        }
    }

    public ObjectList() {
        this.storage = new ArrayList<>();
    }

    public Data getData(int index) {
        return storage.get(index);
    }

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    public void addData(Data data) {
        storage.add(data);
    }

    public static ObjectList getInstance() {
        return list;
    }
}
