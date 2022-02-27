package edu.sjsu.robot.model;

import java.util.List;

public class WeekCurveData {
    private List<String> labels;
    private List<List<Float>> series;



    /**
     * @return List<String> return the labels
     */
    public List<String> getLabels() {
        return labels;
    }

    /**
     * @param labels the labels to set
     */
    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    /**
     * @return List<List<Float>> return the series
     */
    public List<List<Float>> getSeries() {
        return series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(List<List<Float>> series) {
        this.series = series;
    }

}
