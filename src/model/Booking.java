package model;

import java.util.Date;

public class Booking {
    private String bcode;  
    private String pcode;  
    private Date odate;    
    private int paid;      
    private int seat;    

    public Booking() {
    }

    public Booking(String bcode, String pcode, Date odate, int paid, int seat) {
        this.bcode = bcode;
        this.pcode = pcode;
        this.odate = odate;
        this.paid = paid;
        this.seat = seat;
    }

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    @Override
    public String toString() {
        
        return bcode + " | " 
                + pcode + " | " 
                + odate + " | " 
                + paid + " | " 
                + seat;
    }
}