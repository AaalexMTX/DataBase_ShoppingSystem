package Util;

import Information.Order;
import Information.User;

import java.sql.*;

public class OrderUtil {
    //静态代码块 注册驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* 增 order 用 */
    public void insertInOrder(Connection conn, Order order) throws SQLException {
        int count = 0;
        String sql = "insert into t_order values(?,?,?,?,?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, order.getOid());
        ps.setInt(2, order.getOuid());
        ps.setInt(3, order.getOgid());
        ps.setInt(4, order.getNumber());
        //将 Order中的java.sql.Date性时间 转成Timestamp类型再插入数据库
        Timestamp ts = new Timestamp(order.getTime().getTime());
        ps.setTimestamp(5, ts);

        //主键冲突与外键冲突 抛出的异常相同
        try {
            count = ps.executeUpdate();
        } catch (SQLException e) {
            if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                // 处理主键冲突或外键冲突异常
                System.err.println("主键冲突或外键冲突：" + e.getMessage());
            } else {
                // 处理其他 SQL 异常
                e.printStackTrace();
            }
        } finally {
            ps.close();
            System.out.println(count == 1 ? "订单添加成功" : "主码冲突添加失败");
        }
    }
    /* 查 所有订单 */
    public void queryAllInOrder (Connection conn) throws SQLException {
        String sql = "select * from t_order";
        PreparedStatement ps = conn.prepareStatement(sql);;
        ResultSet rs = ps.executeQuery();
        System.out.println("---------------所有订单信息------------------");
        System.out.println("订单编号\t用户id\t货物编号\t购买数量\t购买时间");
        while (rs.next()) {
            String oid = rs.getString(1);
            int ouid = rs.getInt(2);
            int ogid = rs.getInt(3);
            int number = rs.getInt(4);
            Timestamp ts= rs.getTimestamp(5);
            System.out.println(oid + "\t" + ouid + "\t\t" + ogid + "\t\t" + number + "\t\t" + ts);
        }
        rs.close();
        ps.close();
    }
    /* 删 order man用*/
    public void deleteInUserMan (Connection conn,String oid) throws SQLException {
        String sql = "delete from t_order where oid = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, oid);
        int count = ps.executeUpdate();
        System.out.println(count >= 1 ? "删除成功" : "表中无记录");
        ps.close();
    }
    /* 删 order uid用于验证权限 */
    public void deleteInUserCom (Connection conn,String oid,int uid) throws SQLException {
        String sql = "delete from t_order where oid = ? " +
                "and ouid = ? ";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, oid);
        ps.setInt(2,uid);
        int count = ps.executeUpdate();
        System.out.println(count >= 1 ? "退货成功" : "表中无记录");
        ps.close();
    }
    /* 查 用户购买记录 user+order+good 三表连接查询 */
    public void queryMyOrder (Connection conn,int uid) throws SQLException {
        int count = 0; //记录购物条数
        String sql = "select name,account,gid,gname,price,oid,number,time" +
                " from t_user,t_order,t_goods" +
                " where t_user.uid = t_order.ouid and " +
                " t_order.ogid = t_goods.gid and uid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);;
        ps.setInt(1,uid);
        ResultSet rs = ps.executeQuery();
        System.out.println("------------------------------------------所有购物记录信息---------------------------------------------");
        System.out.println("序号\t用户id\t名称\t\t账号\t商品编号\t\t商品名称\t\t  商品价格\t订单编号\t购买数量\t实付金额\t\t购买时间");
        while (rs.next()) {
            String name = rs.getString(1);
            String account = rs.getString(2);
            int gid = rs.getInt(3);
            String gname = rs.getString(4);
            Double price = rs.getDouble(5);
            String oid = rs.getString(6);
            int number = rs.getInt(7);
            Timestamp ts= rs.getTimestamp(8);
            System.out.printf("%2d\t  %d%8s\t%-6s%3d\t\t%-10s\t%-6.2f\t%5s\t%3d\t\t%-6.2f\t%s\n",++count,uid,name,account,gid,gname,price,oid,number,number*price,ts);
        }
        rs.close();
        ps.close();
    }
    /* 改 order 新用户实例 */
    public void updateInUser (Connection conn, Order order) throws SQLException {
        int count = 0;
        String sql = "update t_order set ouid = ?, ogid = ?,number = ? time = ? where oid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, order.getOuid());
        ps.setInt(2, order.getOgid());
        ps.setInt(3, order.getNumber());
        //将 Order中的java.sql.Date性时间 转成Timestamp类型再插入数据库
        Timestamp ts = new Timestamp(order.getTime().getTime());
        ps.setTimestamp(4, ts);
        ps.setString(5, order.getOid());

        //主键冲突与外键冲突 抛出的异常相同
        try {
            count = ps.executeUpdate();
        } catch (SQLException e) {
            if (e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                // 处理主键冲突或外键冲突异常
                System.err.println("主键冲突或外键冲突：" + e.getMessage());
            } else {
                // 处理其他 SQL 异常
                e.printStackTrace();
            }
        } finally {
            ps.close();
            System.out.println(count == 1 ? "订单修改成功" : "主码冲突修改失败");
        }
        ps.close();
    }

    /* check OID是否存在 */
    public boolean checkUid (Connection conn,String oid) throws SQLException {
        boolean check;
        String sql = "select * from t_order where oid = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, oid);
        ResultSet rs = ps.executeQuery();
        check = rs.next() ? true : false;
        rs.close();
        ps.close();
        return check;
    }
    /* oid的自动生成(最后一条的下一条)*/
    public String oidNext(Connection conn) throws SQLException {
        String sql = "select * from t_order";
        PreparedStatement ps = conn.prepareStatement(sql);;
        ResultSet rs = ps.executeQuery();
        String orderNextOid = "0000";
        while (rs.next()) {orderNextOid = rs.getString(1);}

        int num = Integer.parseInt(orderNextOid); // 将字符串转换为整数
        orderNextOid = String.format("%04d", ++num); // 将结果转换回字符串，保持四位数的格式
        rs.close();
        ps.close();
        return orderNextOid;
    }

}
