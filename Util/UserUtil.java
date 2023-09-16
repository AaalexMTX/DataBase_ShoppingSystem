package Util;

import Information.User;
import java.sql.*;
import java.util.Map;
import java.util.Scanner;

public class UserUtil {
    //静态代码块 注册驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /* 增 user */
    public void insertInUser (Connection conn, User user) throws SQLException {
        int count = 0;
        String sql = "insert into t_user(uid,name,account,password,role) values(?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,user.getUid());
        ps.setString(2,user.getName());
        ps.setString(3,user.getAccount());
        ps.setString(4,user.getPassword());
        ps.setInt(5,user.getRole());
        try {
            count = ps.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
        }finally {
            ps.close();
        }
        System.out.println(count == 1?"用户设置成功":"主码(账号)冲突插入失败");
    }
    /* 删 user 由唯一 uid删除user记录 */
    public void deleteInUser(Connection conn,int uid) throws SQLException {
        String sql = "delete from t_user where uid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,uid);
        int count = ps.executeUpdate();
        System.out.println(count >= 1 ? "删除成功":"表中无该用户");
        ps.close();
    }
    /* 查 所有用户 */
    public void queryAllInUser (Connection conn) throws SQLException {
        String sql = "select * from t_user";
        PreparedStatement ps = conn.prepareStatement(sql);;
        ResultSet rs = ps.executeQuery();
        System.out.println("-------------所有用户信息--------------");
        System.out.println("用户id\t名称\t\t账号\t\t 密码\t 身份");
        while (rs.next()) {
            int u = rs.getInt(1);
            String name = rs.getString(2);
            String account = rs.getString(3);
            String password = rs.getString(4);
            int role = rs.getInt(5);
            System.out.printf("%3d\t\t%-5s\t%-8s%-8s%3d\n",u,name,account,password,role);
        }
        rs.close();
        ps.close();
    }
    /* 查 user 由唯一uid查用户信息 */
    public void queryInUser(Connection conn,int uid) throws SQLException {
        String sql = "select * from t_user where uid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,uid);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int u = rs.getInt(1);
            String name = rs.getString(2);
            String account = rs.getString(3);
            String password = rs.getString(4);
            int role = rs.getInt(5);
            System.out.println("\t\tuid 为"+uid+"的用户信息");
            System.out.println(u+"\t"+name+"\t\t"+account+"\t\t"+password+"\t\t"+role);
        }else{
            System.out.println("该uid用户不存在");
        }
        rs.close();
        ps.close();
    }
    /* 查 user 名称+账号+role 模糊查询*/
    public void queryNARInUser(Connection conn) throws SQLException {
        Scanner input = new Scanner(System.in);
        String keyName = "";
        String keyAccount = "";
        int keyRole = 1;
        System.out.print("\t名称关键字: ");
        keyName = input.nextLine();
        System.out.print("\t账户关键字: ");
        keyAccount = input.nextLine();
        System.out.print("\t身份关键字: ");
        keyRole = input.nextInt();
        input.nextLine();   //吃掉缓冲区的回车
        String sql = "select * from t_user where name like ? and account like ? and role = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"%"+keyName+"%");
        ps.setString(2,"%"+keyAccount+"%");
        ps.setInt(3,keyRole);
        ResultSet rs = ps.executeQuery();
        System.out.println("-----------符合条件的用户信息------------");
        System.out.println("用户id\t名称\t\t账号\t\t 密码\t 身份");
        while (rs.next()) {
            int u = rs.getInt(1);
            String name = rs.getString(2);
            String account = rs.getString(3);
            String password = rs.getString(4);
            int role = rs.getInt(5);
            System.out.printf("%3d\t\t%-5s\t%-8s%-8s%3d\n",u,name,account,password,role);
        }
        rs.close();
        ps.close();
    }
    /* 查 由Map账号密码 返回User对象*/
    public User queryUserObject(Connection conn, Map<String,String> AP) throws SQLException {
        String sql = "select * from t_user where account = ? and password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,AP.get("loginAccount"));
        ps.setString(2,AP.get("loginPassword"));
        ResultSet rs = ps.executeQuery();
        User loginUser = new User();
        while(rs.next()){
            int uid = rs.getInt(1);
            String name = rs.getString(2);
            String account = rs.getString(3);
            String password = rs.getString(4);
            int role = rs.getInt(5);
            loginUser = new User(uid,name,account,password,role);
        }
        rs.close();
        ps.close();
        return loginUser;
    }
    /* 改 user信息 */
    public void updateInUser(Connection conn,User user) throws SQLException {
        int count = 0;
        String sql = "update t_user set name = ?,account = ?,password = ?,role =? where uid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,user.getName());
        ps.setString(2,user.getAccount());
        ps.setString(3,user.getPassword());
        ps.setInt(4,user.getRole());
        ps.setInt(5,user.getUid());
        count = ps.executeUpdate();
        System.out.println(count == 1?"信息更新成功":"用户不存在更新失败");
        ps.close();
    }
    /* check UID是否存在 */
    public boolean checkUid(Connection conn,int uid) throws SQLException {
        boolean check;
        String sql = "select * from t_user where uid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,uid);
        ResultSet rs = ps.executeQuery();
        check = rs.next()? true:false;
        rs.close();
        ps.close();
        return check;
    }

    /*
    * 关资源
    * conn 连接对象
    * stat 数据库操作对象
    * rs 结果集
    * */
    public void CloseCSR(Connection conn, Statement stat, ResultSet rs){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }if(stat != null){
            try {
                stat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }if(conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}