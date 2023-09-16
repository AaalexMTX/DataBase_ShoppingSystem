package Information;

public class Goods {
    private int gid;
    private String gname;
    private Double price;
    private int num;
    public Goods(){
        this.gid = 5;
        this.gname = "抽纸";
        this.price = 10.0;
        this.num = 1;}
    public Goods(int gid,String gname,Double price,int num){
        this.gid = gid;
        this.gname = gname;
        this.price = price;
        this.num = num;
    }

    public int getGid() {
        return gid;
    }
    public void setGid(int gid) {
        this.gid = gid;
    }
    public String getGname() {
        return gname;
    }
    public void setGname(String gname) {
        this.gname = gname;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }

}
