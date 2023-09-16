package Information;

import java.util.Date;

public class Order {
    private String oid;
    private int ouid;
    private int ogid;
    private int number;
    private Date time;
    public Order(){
        this.oid = "0002";
        this.ouid = 3;
        this.ogid = 2;
        this.number = 1;
        this.time = new Date();}
    public Order(String oid,int ouid,int ogid,int number,Date time){
        this.oid = oid;
        this.ouid = ouid;
        this.ogid = ogid;
        this.number = number;
        this.time = time;
    }

    public int getOuid() {
        return ouid;
    }
    public void setOuid(int ouid) {
        this.ouid = ouid;
    }
    public String getOid() {
        return oid;
    }
    public void setOid(String oid) {
        this.oid = oid;
    }
    public int getOgid() {
        return ogid;
    }
    public void setOgid(int ogid) {
        this.ogid = ogid;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return oid+"\t"+ouid+"\t"+ogid+"\t"+number+"\t"+time;
    }
}
