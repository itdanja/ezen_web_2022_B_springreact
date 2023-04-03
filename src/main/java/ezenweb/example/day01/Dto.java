package ezenweb.example.day01;

public class Dto {
    private int mno;
    private String mid;
    private String mpassword;
    private long point;
    private String phone;

    public Dto() {}
    public Dto(int mno, String mid, String mpassword, long point, String phone) {
        this.mno = mno;
        this.mid = mid;
        this.mpassword = mpassword;
        this.point = point;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "mno=" + mno +
                ", mid='" + mid + '\'' +
                ", mpassword='" + mpassword + '\'' +
                ", point=" + point +
                ", phone='" + phone + '\'' +
                '}';
    }

    public int getMno() {
        return mno;
    }

    public void setMno(int mno) {
        this.mno = mno;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMpassword() {
        return mpassword;
    }

    public void setMpassword(String mpassword) {
        this.mpassword = mpassword;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
