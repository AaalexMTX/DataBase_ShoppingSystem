import Information.User;
import UI.Common;
import UI.Manager;
import Util.UserUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
*登录界面
* 
*
* 注册--插入user
*
* 购物车--加表?
* 货物状态 -- 给order加sign属性栏
*
* 管理员禁用账户 -- user表加属性
*
* 复杂的sql语句的使用
*
* 考虑数据库设计的理论缺陷？ 范式?
*
* 可视化的实现
* */
public class MAIN {
    //收集账号密码
    public static Map<String,String> initUI(){
        Scanner input = new Scanner(System.in);
        System.out.println("----用户登录:----");
        System.out.print("输入账户:");
        String loginAccount = input.nextLine();
        System.out.print("输入密码:");
        String loginPassword = input.nextLine();

        Map<String,String> userLogin = new HashMap<String, String>();
        userLogin.put("loginAccount",loginAccount);
        userLogin.put("loginPassword",loginPassword);
        return userLogin;
    }
    //验证身份
    public static int loginCheck(Map<String,String> userLogin){
        int UserRole = -1; // 角色身份标志位
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
            String sql = "select * from t_user where account = ? and password = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,userLogin.get("loginAccount"));
            ps.setString(2,userLogin.get("loginPassword"));
            ResultSet rs = ps.executeQuery();
            //用 账号 密码 查user表 得到该记录下role值
            if(rs.next()){
                UserRole = rs.getInt(5);
            }else {
                UserRole = -1;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return UserRole;
    }
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        Common common = new Common();
        Manager manager = new Manager();
        UserUtil userUtil = new UserUtil();

        //登录界面
        int n =0;
        do {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
            Map<String,String> AP = initUI();
            int ROLE = loginCheck(AP);
            switch (ROLE){
                case 0:
                    System.out.println("管理员登录成功\n\n\n");
                    manager.manRun();
                    break;
                case 1:
                    System.out.println("用户登录成功\n\n\n\n\n\n");
                    User user = userUtil.queryUserObject(conn,AP);
                    common.comRun(user);
                    break;
                case -1:
                    System.out.println("用户不存在");
                    break;
                default:
                    System.out.println("错误退出");
                    break;
            }
        }while (n++ < 5);
    conn.close();


        //        //注册驱动
        //        //通过反射机制类加载 实现注册（由于只需要动作不需要接收返回值
        //        Class.forName("com.mysql.cj.jdbc.Driver");
        //        //获取连接
        //        String url = "jdbc:mysql://127.0.0.1:3306/id_connect_test";
        //        String username = "root";
        //        String password = "111111";
        //        Connection conn = DriverManager.getConnection(url, username, password);
        //        //定义sql
        //        String sql = "update information set age = 18 where id = 1";
        //        //获取操作对象
        //        Statement stat = conn.createStatement();
        //        //执行sql
        //        int count = stat.executeUpdate(sql);
        //        //处理结果
        //
        //        System.out.println(count);
        //        //释放资源
        //        stat.close();
        //        conn.close();

        //2

        //        Statement stat = null;
        //        Connection conn = null;
        //        try{
        //            //注册驱动
        //            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        //            //获取通道
        //            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
        //            //获取操作对象
        //            stat = conn.createStatement();
        //            //创建SQL
        //            int count = stat.executeUpdate("update information set age = 1086 where id = 1");
        //            //查看资源
        //            System.out.println("变化"+count);
        //        }catch (SQLException e){
        //            e.printStackTrace();
        //        }finally {
        //            try {
        //                stat.close();
        //            }catch (NullPointerException e){
        //                e.printStackTrace();
        //            }
        //            conn.close();
        //        }

    }
}
