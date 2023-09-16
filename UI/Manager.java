package UI;

import Information.Goods;
import Information.Order;
import Information.User;
import Util.GoodUtil;
import Util.OrderUtil;
import Util.UserUtil;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Manager {
    public void manRun() throws SQLException {
        Scanner input = new Scanner(System.in);
        UserUtil userUtil = new UserUtil();
        GoodUtil goodUtil = new GoodUtil();
        OrderUtil orderUtil = new OrderUtil();
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
        int choice ;
        do {
            choice = 0;
            System.out.println("*-----* 菜单 *------*");
            System.out.println("\t1. 管理用户");
            System.out.println("\t2. 管理商品");
            System.out.println("\t3. 管理记录");
            System.out.println("\t4. ......");
            System.out.println("\t0. 退出账户");
            System.out.println("*---------------*");
            System.out.print("请输入选择：");

            //菜单异常输入 检测
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("菜单选项-非法输入\n");
            } finally {
                input.nextLine();   //消掉缓冲区空格 否则异常情况下do-while死循环
            }
            switch (choice){
                case 1:
                    manUser();
                    break;
                case 2:
                    manGood();
                    break;
                case 3:
                    manOrder();
                    break;
                case 4:
                    break;
                case 0:
                    System.out.println("账号退出成功");
                    break;
                default:
                    System.out.println("功能暂无");
                    break;
            }
            System.out.print("\n");
        }while (choice != 0);
        conn.close();
        System.out.println("\n");
    }
    // 用户信息管理
    public void manUser() throws SQLException {
        Scanner input = new Scanner(System.in);
        UserUtil userUtil = new UserUtil();
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
        int choice;
        do {
            choice = -1;
            System.out.println("*-----* 菜单 *------*");
            System.out.println("\t1. 查询所有用户信息");
            System.out.println("\t2. 增加用户");
            System.out.println("\t3. 删除用户");
            System.out.println("\t4. 查询 符合条件用户");
            System.out.println("\t5. 修改用户信息");
            System.out.println("\t0. 退出用户管理");
            System.out.println("*---------------*");
            System.out.print("请输入选择：");

            //菜单异常输入 检测
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("菜单选项-非法输入\n");
            } finally {
                input.nextLine();   //消掉缓冲区空格 否则异常情况下do-while死循环
            }

            switch (choice){
                case 1:
                    userUtil.queryAllInUser(conn);
                    break;
                case 2:
                    int addUid = 0;     //用户uid
                    System.out.print("请输入录入用户uid：");
                    //非法输入uid 检测
                    try{
                        addUid = input.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("录入int型uid时非法输入 -- 请重新输入\n");
                        break;
                    }finally {
                        input.nextLine();
                    }
                    //uid唯一 检测
                    if(userUtil.checkUid(conn,addUid)){
                        System.out.println("用户已存在 请重新录入");
                        break;
                    }

                    System.out.print("\tname：");
                    String name = input.nextLine();
                    System.out.print("\taccount：");
                    String account = input.nextLine();
                    System.out.print("\tpassword：");
                    String password = input.nextLine();
                    System.out.print("\trole：");
                    int role = input.nextInt();
                    User userInsert = new User(addUid,name,account,password,role);
                    userUtil.insertInUser(conn,userInsert);
                    break;
                case 3:
                    int deleteUid = 0;     //用户uid
                    System.out.print("请输入删除用户uid：");
                    //非法输入uid 检测
                    try{
                        deleteUid = input.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("录入int型uid时非法输入 -- 请重新输入\n");
                        break;
                    }finally {
                        input.nextLine();
                    }
                    userUtil.deleteInUser(conn,deleteUid);
                    break;
                case 4:
                    userUtil.queryNARInUser(conn);
                    break;
                case 5:
                    userUtil.queryAllInUser(conn);
                    int updateUid = 0;     //用户uid
                    System.out.print("请输入更新用户uid:");
                    //非法输入uid 检测
                    try{
                        updateUid = input.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("录入int型uid时非法输入 -- 请重新输入\n");
                        break;
                    }finally {
                        input.nextLine();
                    }
                    //uid唯一 检测
                    if(!userUtil.checkUid(conn,updateUid)){
                        System.out.println("用户不存在 请重新录入");
                        break;
                    }

                    System.out.print("\tname：");
                    String uName = input.nextLine();
                    System.out.print("\taccount：");
                    String uAccount = input.nextLine();
                    System.out.print("\tpassword：");
                    String uPassword = input.nextLine();
                    System.out.print("\trole：");
                    int uRole = input.nextInt();
                    User userUpdate = new User(updateUid,uName,uAccount,uPassword,uRole);
                    userUtil.updateInUser(conn,userUpdate);
                    break;
                case 0:
                    System.out.println("用户管理功能退出成功");
                    break;
                default:
                    System.out.println("功能暂无");
                    break;
            }
            System.out.print("\n");
        }while (choice != 0);
        conn.close();
        System.out.println("\n");
    }
    // 货物管理
    public void manGood() throws SQLException{
        Scanner input = new Scanner(System.in);
        GoodUtil goodUtil = new GoodUtil();
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
        int choice;
        do {
            choice = 0;
            System.out.println("*-----* 菜单 *------*");
            System.out.println("\t1. 查询所有货物信息");
            System.out.println("\t2. 增加货物");
            System.out.println("\t3. 删除货物");
            System.out.println("\t4. 查询 符合条件货物");
            System.out.println("\t5. 修改货物信息");
            System.out.println("\t0. 退出货物管理");
            System.out.println("*---------------*");
            System.out.print("请输入选择：");

            //菜单异常输入 检测
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("菜单选项-非法输入\n");
            } finally {
                input.nextLine();   //消掉缓冲区空格 否则异常情况下do-while死循环
            }

            switch (choice){
                case 1:
                    goodUtil.queryAllInGood(conn);
                    break;
                case 2:
                    int addGid = 0;     //货物Gid
                    System.out.print("请输入录入货物Gid：");
                    //非法输入gid 检测
                    try{
                        addGid = input.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("录入int型uid时非法输入 -- 请重新输入\n");
                        break;
                    }finally {
                        input.nextLine();
                    }
                    //gid唯一 检测
                    if(goodUtil.checkGid(conn,addGid)){
                        System.out.println("货物已存在 请重新录入");
                        break;
                    }

                    System.out.print("\tgName: ");
                    String gName = input.nextLine();
                    System.out.print("\tprice: ");
                    Double price = input.nextDouble();
                    System.out.print("\tnum: ");
                    int num = input.nextInt();
                    Goods addGood = new Goods(addGid,gName,price,num);
                    goodUtil.insertInGood(conn,addGood);
                    break;
                case 3:
                    int deleteGid = 0;     //货物Gid
                    System.out.print("请输入删除货物Gid: ");
                    //非法输入gid 检测
                    try{
                        deleteGid = input.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("录入int型uid时非法输入 -- 请重新输入\n");
                        break;
                    }finally {
                        input.nextLine();
                    }
                    goodUtil.deleteInGood(conn,deleteGid);
                    break;
                case 4:
                    goodUtil.queryGNameInGood(conn);
                    break;
                case 5:
                    goodUtil.queryAllInGood(conn);
                    int updateGid = 0;     //货物gid
                    System.out.print("请输入更新货物Gid: ");
                    //非法输入uid 检测
                    try{
                        updateGid = input.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("录入int型gid时非法输入 -- 请重新输入\n");
                        break;
                    }finally {
                        input.nextLine();
                    }
                    //uid唯一 检测
                    if(!goodUtil.checkGid(conn,updateGid)){
                        System.out.println("货物不存在 请重新录入");
                        break;
                    }

                    System.out.print("\tgName: ");
                    String upName = input.nextLine();
                    System.out.print("\tprice: ");
                    Double upPrice = input.nextDouble();
                    System.out.print("\tnum: ");
                    int upNum = input.nextInt();
                    Goods upGood = new Goods(updateGid,upName,upPrice,upNum);
                    goodUtil.updateInGood(conn,upGood);
                    break;
                case 0:
                    System.out.println("货物管理功能-退出成功");
                    break;
                default:
                    System.out.println("功能暂无");
                    break;
            }
            System.out.print("\n");
        }while (choice != 0);
        conn.close();
        System.out.println("\n");
    }
    // 订单管理
    public void manOrder() throws SQLException{
        Scanner input = new Scanner(System.in);
        OrderUtil orderUtil = new OrderUtil();
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
        int choice;
        do {
            choice = 0;
            System.out.println("*-----* 菜单 *------*");
            System.out.println("\t1. 查询所有订单信息");
            System.out.println("\t2. 增加订单");
            System.out.println("\t3. 删除订单");
            System.out.println("\t4. --");
            System.out.println("\t0. 退出订单管理");
            System.out.println("*---------------*");
            System.out.print("请输入选择：");

            //菜单异常输入 检测
            try {
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("菜单选项-非法输入\n");
            } finally {
                input.nextLine();   //消掉缓冲区空格 否则异常情况下do-while死循环
            }

            switch (choice){
                case 1:
                    orderUtil.queryAllInOrder(conn);
                    break;
                case 2:
                    String addOid ;     //订单Oid
                    System.out.print("请输入录入订单Oid(4位 int): ");
                    //非法输入oid 检测
                    addOid = input.nextLine();
                    if(addOid.length()!= 4){
                        System.out.println("录入Oid时非法输入 -- 请重新输入\n");
                        break;
                    }
                    //gid唯一 检测
                    if(orderUtil.checkUid(conn,addOid)){
                        System.out.println("货物已存在 请重新录入");
                        break;
                    }

                    System.out.print("\toUid: ");
                    int oUid = input.nextInt();
                    System.out.print("\toGid: ");
                    int oGid = input.nextInt();
                    System.out.print("\tnumber: ");
                    int number = input.nextInt();
                    System.out.println("\ttime: ");
                    Date time = new Date();
                    //将 Order中的java.sql.Date性时间 转成Timestamp类型再插入数据库
                    Timestamp ts = new Timestamp(time.getTime());

                    Order order = new Order(addOid,oUid,oGid,number,ts);
                    orderUtil.insertInOrder(conn,order);
                    break;
                case 3:
                    String deleteGid;     //订单Oid
                    System.out.print("请输入删除订单Oid(4位 int): ");
                    //非法输入oid 检测
                    deleteGid = input.nextLine();
                    if(deleteGid.length()!= 4){
                        System.out.println("录入Oid时非法输入 -- 请重新输入\n");
                        break;
                    }
                    //gid唯一 检测
                    if(!orderUtil.checkUid(conn,deleteGid)){
                        System.out.println("货物不存在 请重新录入");
                        break;
                    }
                    orderUtil.deleteInUserMan(conn,deleteGid);
                    break;
                case 4:
                    orderUtil.queryAllInOrder(conn);
                    String updateOid ;     //订单Oid
                    System.out.print("请输入修改订单Oid: ");
                    //非法输入oid 检测
                    updateOid = input.nextLine();
                    //非法输入oid 检测
                    updateOid = input.nextLine();
                    if(updateOid.length()!= 4){
                        System.out.println("录入Oid时非法输入 -- 请重新输入\n");
                        break;
                    }
                    //gid唯一 检测
                    if(orderUtil.checkUid(conn,updateOid)){
                        System.out.println("货物已存在 请重新录入");
                        break;
                    }

                    System.out.print("\toUid: ");
                    int upUid = input.nextInt();
                    System.out.print("\toGid: ");
                    int upGid = input.nextInt();
                    System.out.print("\tnumber: ");
                    int upNumber = input.nextInt();
                    System.out.println("\ttime: ");
                    Date upTime = new Date();
                    //将 Order中的java.sql.Date性时间 转成Timestamp类型再插入数据库
                    Timestamp TS = new Timestamp(upTime.getTime());

                    Order upOrder = new Order(updateOid,upUid,upGid,upNumber,TS);
                    orderUtil.updateInUser(conn,upOrder);
                    break;
                case 0:
                    System.out.println("货物管理功能-退出成功");
                    break;
                default:
                    System.out.println("功能暂无");
                    break;
            }
            System.out.print("\n");
        }while (choice != 0);
        conn.close();
        System.out.println("\n");
    }
}