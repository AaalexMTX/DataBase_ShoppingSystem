# 数据库课程实践大作业
```java
(本地的) - java(IDEA)+JDBC+Mysql
```
# 备注
有点久远了，第一次往github放代码属于是代码写完了，直接copy到github上来的
1. mysql的建表语句忘了丢上来
2. 全程控制台输入交互，体验感略差
3. 只实现了CURD的接口，没啥特别的

## 购物系统的开发与设计
-------功能包括-------<br>
用户：<br>
![用户可用功能](https://github.com/Andouls/DataBase_ShoppingSystem/blob/main/java%E8%B4%AD%E7%89%A9%E7%B3%BB%E7%BB%9F/%E7%94%A8%E6%88%B7%E9%80%89%E9%A1%B9.jpg)
<br>
管理员:<br>
![管理界面](https://github.com/Andouls/DataBase_ShoppingSystem/blob/main/java%E8%B4%AD%E7%89%A9%E7%B3%BB%E7%BB%9F/%E7%AE%A1%E7%90%86%E7%95%8C%E9%9D%A2.jpg)
<br>
管理员操作:<br>
![管理功能细分](https://github.com/Andouls/DataBase_ShoppingSystem/blob/main/java%E8%B4%AD%E7%89%A9%E7%B3%BB%E7%BB%9F/%E7%AE%A1%E7%90%86%E7%BB%86%E5%88%86%E5%8A%9F%E8%83%BD.jpg)
<br>
账号登录:<br>
![登录界面](https://github.com/Andouls/DataBase_ShoppingSystem/blob/main/java%E8%B4%AD%E7%89%A9%E7%B3%BB%E7%BB%9F/%E7%99%BB%E5%BD%95%E7%95%8C%E9%9D%A2.jpg)
<br>
-------数据库结构设计-----------<br>
数据库UML图<br>
![UML](https://github.com/Andouls/DataBase_ShoppingSystem/blob/main/java%E8%B4%AD%E7%89%A9%E7%B3%BB%E7%BB%9F/%E6%95%B0%E6%8D%AE%E5%BA%93UML%E5%9B%BE.png)
<br>
数据库ER图<br>
![ER](https://github.com/Andouls/DataBase_ShoppingSystem/blob/main/java%E8%B4%AD%E7%89%A9%E7%B3%BB%E7%BB%9F/%E6%95%B0%E6%8D%AE%E5%BA%93ER%E5%9B%BE.png)<br>
--------涉及SQL语句包括-------<br>
1.增 insert<br>
2.删 delect<br>
3.改 update<br>
4.查 select<br>
5.多表连接查询<br>
6.触发器（买商品时（插入一条购物记录）减少货物表中对应物品数量）<br>
