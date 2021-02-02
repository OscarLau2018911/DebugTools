package com.oscar.widgets.utils;

import android.graphics.Point;


public class ClickUtils {
    public static final long CLICKDURATION = 200l;
    public static final long CLICKDISTANCE = 100l;
    private clickData data;
    public ClickUtils(clickData data) {
        this.data = data;
    }
    public boolean isClick() {
        return ((this.data.getEndT() - this.data.getStartT()) < CLICKDURATION) &&
                (Math.sqrt((this.data.getEnd().x-this.data.getStart().x)*(this.data.getEnd().x-this.data.getStart().x)+(this.data.getEnd().y-this.data.getStart().y)*(this.data.getEnd().y-this.data.getStart().y)) < CLICKDISTANCE);
    }
    public boolean isClick(Point start,Point end,long startTime,long endTime) {
        return ((endTime - startTime) < CLICKDURATION) &&
                (Math.sqrt((end.x-start.x)*(end.x-start.x)+(end.y-start.y)*(end.y-start.y)) < CLICKDISTANCE);
    }

    public static class Builder{
        private clickData data;
        public Builder() {
            data = new clickData();
        }
        public void setStartP(Point start) {
            this.data.setStart(start);
        }
        public void setEndP(Point end) {
            this.data.setEnd(end);
        }
        public void setStartTime(long startTime) {
            this.data.setStartT(startTime);
        }
        public void setEndTime(long endTime) {
            this.data.setEndT(endTime);
        }
        public ClickUtils create() {
            ClickUtils clickUtils = new ClickUtils(this.data);
            return clickUtils;
        }
    }

    static class clickData{
        private Point start;
        private Point end;
        private long startT;
        private long endT;

        public clickData() {

        }

        public Point getStart() {
            return start;
        }

        public void setStart(Point start) {
            this.start = start;
        }

        public Point getEnd() {
            return end;
        }

        public void setEnd(Point end) {
            this.end = end;
        }

        public long getStartT() {
            return startT;
        }

        public void setStartT(long startT) {
            this.startT = startT;
        }

        public long getEndT() {
            return endT;
        }

        public void setEndT(long endT) {
            this.endT = endT;
        }
    }
}
