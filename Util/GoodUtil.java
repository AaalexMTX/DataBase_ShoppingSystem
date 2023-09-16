package Util;

import Information.Goods;
import Information.Order;
import Information.User;

import java.sql.*;
import java.util.Scanner;

/*
* 货物工具类
* t_good表的操作方法
* */
public class GoodUtil {
    Scanner input = new Scanner(System.in);
    //静态代码块 注册驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* 增 good */
    public void insertInGood (Connection conn, Goods goods) throws SQLException {
        int count = 0;
        String sql = "insert into t_goods values(?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,goods.getGid());
        ps.setString(2,goods.getGname());
        ps.setDouble(3,goods.getPrice());
        ps.setInt(4,goods.getNum());
        try {
            count = ps.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            //e.printStackTrace();
        }finally {
            ps.close();
        }
        System.out.println(count == 1?"插入成功":"主码冲突插入失败");
    }
    /* 删 good 由唯一 gid删除good记录 */
    public void deleteInGood(Connection conn,int gid) throws SQLException {
        String sql = "delete from t_goods where gid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,gid);
        int count = ps.executeUpdate();
        System.out.println(count >= 1 ? "删除成功":"表中无该商品");
        ps.close();
    }
    /* 查 所有商品 */
    public void queryAllInGood (Connection conn) throws SQLException {
        String sql = "select * from t_goods";
        PreparedStatement ps = conn.prepareStatement(sql);;
        ResultSet rs = ps.executeQuery();
        System.out.println("-----------所有商品信息------------");
        System.out.println("商品编号\t\t商品名称\t\t商品价格\t库存数量");
        while (rs.next()) {
            int gid = rs.getInt(1);
            String gname = rs.getString(2);
            Double price = rs.getDouble(3);
            int number = rs.getInt(4);
            System.out.printf("%3d\t\t%-10s\t%-6.2f\t%4d\n",gid,gname,price,number);
        }
        rs.close();
        ps.close();
    }
    /* 查 good 由唯一gid查 某条商品信息 */
    public void queryGidInGood(Connection conn,int gid) throws SQLException {
        String sql = "select * from t_goods where gid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,gid);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            String gname = rs.getString(2);
            Double price = rs.getDouble(3);
            int number = rs.getInt(4);
            System.out.println(gid + "\t" + gname + "\t\t" + price + "\t\t" + number);
        }else{
            System.out.println("该gid商品不存在");
        }
        rs.close();
        ps.close();
    }
    /*查 名称+价格 模糊查询+最低价格*/
    public void queryGNameInGood(Connection conn) throws SQLException {
        String keyWord = "";
        Double lowestPrice = 0.0;
        System.out.print("名称关键字: ");
        keyWord = input.nextLine();
        System.out.print("输入最低价格标准: ");
        lowestPrice = input.nextDouble();
        input.nextLine();   //吃掉缓冲区的回车
        String sql = "select * from t_goods where gname like ? and price >= ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,"%"+keyWord+"%");
        ps.setDouble(2,lowestPrice);
        ResultSet rs = ps.executeQuery();
        System.out.println("-----------符合检索条件的商品信息------------");
        System.out.println("商品编号\t\t商品名称\t\t商品价格\t库存数量");
        while (rs.next()){
            int gid = rs.getInt(1);
            String gname = rs.getString(2);
            Double price = rs.getDouble(3);
            int number = rs.getInt(4);
            System.out.printf("%3d\t\t%-10s\t%-6.2f\t%4d\n",gid,gname,price,number);
        }
        rs.close();
        ps.close();
    }
    /*改 good 新用户实例  */
    public void updateInGood(Connection conn,Goods goods) throws SQLException {
        int count = 0;
        String sql = "update t_goods set gname = ?,price = ?,num = ? where gid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,goods.getGname());
        ps.setDouble(2,goods.getPrice());
        ps.setInt(3,goods.getNum());
        ps.setInt(4,goods.getGid());
        count = ps.executeUpdate();
        System.out.println(count == 1?"更新成功":"用户不存在更新失败");
        ps.close();
    }

    /* check gID是否存在  */
    public boolean checkGid(Connection conn,int gid) throws SQLException {
        boolean check;
        String sql = "select * from t_goods where gid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1,gid);
        ResultSet rs = ps.executeQuery();
        check = rs.next()? true:false;
        rs.close();
        ps.close();
        return check;
    }

    /* 关资源
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