package com.ravisharma.saveexcelcontacts;

/**
 * Created by Ravi Sharma on 06-Mar-18.
 */

public class Option implements Comparable<Option> {
    private String data;
    private boolean folder;
    private String name;
    private boolean parent;
    private String path;

    public Option(String n, String d, String p, boolean folder, boolean parent) {
        this.name = n;
        this.data = d;
        this.path = p;
        this.folder = folder;
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    public String getData() {
        return this.data;
    }

    public String getPath() {
        return this.path;
    }

    public int compareTo(Option o) {
        if (this.name != null) {
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
        }
        throw new IllegalArgumentException();
    }

    public boolean isFolder() {
        return this.folder;
    }

    public boolean isParent() {
        return this.parent;
    }
}
