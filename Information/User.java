package Information;

public class User {
    private int uid;
    private String name;
    private String account;
    private String password;
    private int role;
    public User(){
        this.uid = 5;
        this.name = "abc";
        this.account = "zhaaa";
        this.password = "123456";
        this.role = 1;}
    public User(int uid,String name,String account,String password,int role){
        this.uid = uid;
        this.name = name;
        this.account = account;
        this.password = password;
        this.role = role;
    }

    public int getUid(){return uid;}
    public void setUid(int uid){this.uid = uid;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getAccount(){return account;}
    public void setAccount(String account){this.account = account;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}
    public int getRole(){return role;}
    public void setRole(int role){this.role = role;}

    @Override
    public String toString(){
        return uid+"\t"+name+"\t"+account+"\t";
    }
}
