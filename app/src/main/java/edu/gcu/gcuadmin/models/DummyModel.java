package edu.gcu.gcuadmin.models;

/**
 * Created by Shrivats on 4/11/2017.
 */

public class DummyModel {
    private Float value;
    private String Source;

    public DummyModel(Float value, String source) {
        this.value = value;
        Source = source;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }
}
