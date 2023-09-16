package UI;

import Information.Goods;
import Information.Order;
import Information.User;
import Util.GoodUtil;
import Util.OrderUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Common {

    public void comRun(User loginUser) throws SQLException {
        Scanner input = new Scanner(System.in);
        GoodUtil goodUtil = new GoodUtil();
        OrderUtil orderUtil = new OrderUtil();
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/id_connect_test","root","111111");
        int choice = 0;
        do {
            goodUtil.queryAllInGood(conn);
            choice = 0;
            System.out.println("*-----* 菜单 *------*");
            System.out.println("\t1. 购买商品");
            System.out.println("\t2. 检索商品");
            System.out.println("\t3. 查看购买记录");
            System.out.println("\t4. 申请退货");
            System.out.println("\t0. 退出账户");
            System.out.println("*---------------*");
            System.out.print("请输入你的选择：");

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
                    int addOrderGid = 0;     //商品gid
                    System.out.print("\t购买商品的编号: ");
                    //非法输入gid 检测
                    try{
                        addOrderGid = input.nextInt();
                    }catch (InputMismatchException e){
                        System.out.println("录入int型gid时非法输入 -- 请重新输入\n");
                        break;
                    }finally {
                        input.nextLine();
                    }
                    //gid存在性 检测
                    if(!goodUtil.checkGid(conn,addOrderGid)){
                        System.out.println("该编号的商品不存在 请重新录入");
                        break;
                    }

                    System.out.print("\t购买数量: ");
                    int number = input.nextInt();
                    Order order = new Order(orderUtil.oidNext(conn),loginUser.getUid(),addOrderGid,number,new Date());
                    orderUtil.insertInOrder(conn,order);
                    break;
                case 2:
                    goodUtil.queryGNameInGood(conn);
                    break;
                case 3:
                    orderUtil.queryMyOrder(conn,loginUser.getUid());
                    break;
                case 4:
                    String deleteOrderOid = "";     //订单Oid
                    System.out.print("\t退货订单的编号: ");
                    deleteOrderOid = input.nextLine();
                    orderUtil.deleteInUserCom(conn,deleteOrderOid,loginUser.getUid());
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
        System.out.print("\n\n");
    }
}
