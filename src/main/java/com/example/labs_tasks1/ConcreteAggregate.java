package com.example.labs_tasks1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConcreteAggregate implements Aggregate {
    private String filetop;
    private List<File> images;

    public ConcreteAggregate(String filetop) {
        this.filetop = filetop;
        this.images = new ArrayList<>();
        loadImages(new File(filetop));
    }

    private void loadImages(File directory) {
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    loadImages(file);
                } else if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {
                    images.add(file);
                }
            }
        }
    }

    @Override
    public Iterator getIterator() {
        return new ImageIterator();
    }

    private class ImageIterator implements Iterator {
        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < images.size();
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return images.get(current++);
            }
            return null;
        }
    }
}