package com.zxc.dto;

/**
 * Created by zxc on 16/7/12.
 */
public class Exposer {

    private boolean exposed;

    private String md5;

    private long seckillId;

    private long now;

    public long getSeckillId() {
        return seckillId;
    }

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;

    }

    public Exposer(boolean exposed, long seckillId, long start, long now, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.start = start;
        this.now = now;
        this.end = end;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    private long start;

    private long end;

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
