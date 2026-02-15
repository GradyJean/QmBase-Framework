JavaSE

1) String / StringBuilder / StringBuffer 的区别
   •	String：不可变（immutable），线程安全；拼接会产生新对象，频繁拼接慢。
   •	StringBuilder：可变，非线程安全；单线程场景下拼接最快。
   •	StringBuffer：可变，线程安全（方法加 synchronized）；多线程可用但开销大。

2) 抽象类 vs 接口
   •	抽象类：可有成员变量/构造器/普通方法；方法可有任何可见性；单继承；适合“is-a + 共享状态/默认实现”。
   •	接口：字段默认 public static final；方法默认 public abstract；Java 8 起支持 default/static 方法，Java 9 起支持 private 帮助方法；多实现；适合“can-do 能力集合”。

3) 接口的成员变量和方法的访问权限
   •	变量：隐式 public static final（必须初始化，常量）。
   •	方法：隐式 public abstract；Java8+ 允许 default/static；Java9+ 允许 private（仅供 default 方法复用）。

4) 内部类能否访问外部类的私有变量？
   •	可以。编译器会为内部类合成访问器（synthetic method）并在内部类里保存对外部类实例的引用（Outer.this）。
   局部内部类/匿名类访问方法局部变量时，要求该变量 effectively final（编译器拷贝一份进入内部类）。

5) 深拷贝 vs 浅拷贝
   •	浅拷贝：拷贝基本类型值 & 对象引用；多个对象共享同一引用指向的可变对象。
   •	深拷贝：连同引用指向的对象也递归拷贝；互不影响。
   常见实现：手写拷贝构造、序列化反序列化、clone() + 自己处理可变字段。

6) HashMap 如何处理哈希冲突？
   •	JDK8：数组 + 链表/红黑树（链表长度 > 8 且桶容量 ≥ 64 时树化；< 6 退化为链表）。
   •	负载因子默认 0.75（时间/空间折中），扩容时容量翻倍并再哈希。

7) 常见哈希冲突解决方法
   •	拉链法（separate chaining）：数组 + 链表/树（HashMap 用它）。
   •	开放定址（open addressing）：线性探测、二次探测、双重散列（如 ThreadLocalMap）。
   •	再哈希（rehash）/扩容、布隆过滤器预判等。

8) 常见线程安全的集合
   •	ConcurrentHashMap、ConcurrentSkipListMap/Set
   •	CopyOnWriteArrayList/Set
   •	BlockingQueue（Array/Linked, Delay, Priority 等）
   •	Collections.synchronizedList/Map（外层全局锁，粒度粗）
   •	老牌：Vector、Hashtable（不推荐）

⸻

JVM

1) JVM 内存结构
   •	线程私有：程序计数器、虚拟机栈（栈帧/局部变量表/操作数栈）、本地方法栈。
   •	线程共享：堆（对象实例、TLAB）、方法区/元空间（类元数据、常量池）、代码缓存（JIT 产物）。

2) 堆和栈的区别
   •	堆：存放对象实例；GC 管理；线程共享；分配慢但灵活。
   •	栈：存放栈帧/局部变量/方法调用；随调用入栈出栈；线程私有；速度快。

3) 方法区的作用
   •	存类元数据（字段/方法/常量池等）。JDK8 起改为 Metaspace（元空间，使用本地内存），替代 PermGen。
   •	与之相关：运行时常量池在方法区；StringTable 独立放堆。

4) 双亲委派机制
   •	Bootstrap → Ext → App → 自定义；类加载请求自下而上委派，已加载则直接返回。
   •	目的：安全/一致性（如 java.lang.* 只能由引导类加载器加载）。

5) 常见 GC 算法
   •	标记–清除、标记–整理、复制算法、分代收集。
   •	关键概念：可达性分析（GC Roots）、STW、写屏障/读屏障、三色标记。

6) 垃圾回收器
   •	Serial/ParNew、Parallel Scavenge、CMS（老牌）、G1（服务端默认）
   •	ZGC、Shenandoah（低延迟，TB 级堆，Region/着色指针/负载屏障）

⸻

MySQL

1) 事务 ACID
   •	Atomicity：原子性（全部成功/全部失败；undo 日志）
   •	Consistency：一致性（前后约束满足）
   •	Isolation：隔离性（隔离级别、锁/MVCC）
   •	Durability：持久性（redo/ binlog、刷盘）

2) 三大范式（简版）
   •	1NF：列原子性（不可再分）
   •	2NF：非主属性完全依赖主键（无部分依赖）
   •	3NF：非主属性不传递依赖于主键

业务上常为性能做反范式（冗余字段、汇总表）。

3) 隔离级别
   •	RU / RC / RR（InnoDB 默认）/ Serializable
   •	MVCC 实现 RC/RR 的一致性读（快照读）；RR + Next-Key Lock 抑制幻读（当前读）。

4) 常见索引
   •	主键（聚簇）、唯一索引、普通索引、联合索引、前缀索引、全文索引、空间索引。
   •	最左前缀原则：联合索引 (a,b,c) 能用 a / a,b；b,c 单独用不上（除非有覆盖索引场景）。

5) 唯一索引如何保证“不重复”
   •	依赖唯一索引约束 + InnoDB 的锁：插入前会查唯一索引记录并对目标位置加 插入意向锁 + Gap/Record/Next-Key 锁，并发插入相同 key 会冲突阻塞/报错。
   •	更新唯一列同理；唯一检查与插入在同一事务内保证原子性。

6) 索引的数据结构
   •	主流：B+Tree（InnoDB 主/二级索引）、倒排索引（全文）、Hash（Memory 引擎）、R-Tree（空间索引）。
   •	B+Tree 特点：所有数据在叶子结点、叶子链表顺序扫描快、扇出大 IO 少。

7) MySQL 锁的分类
   •	粒度：表锁、行锁（Record/GAP/Next-Key/Insert Intention）、MDL（元数据锁）。
   •	模式：S 共享锁、X 排他锁；意向锁（IS/IX）；乐观/悲观为思想。
   •	当前读（select ... for update/share）会加锁；快照读不加行锁。

8) UPDATE 加锁后是否可以读？
   •	事务 T1 执行 UPDATE ...（当前读，持 X 锁）。
   •	事务 T2：
   •	普通 SELECT（快照读，RC/RR）：可以读，读到的是历史版本（MVCC），不阻塞。
   •	SELECT ... FOR UPDATE/LOCK IN SHARE MODE（当前读）：会被阻塞，直到 T1 释放锁。

读什么、会不会被挡，取决于“是否当前读”。

⸻

操作系统

进程间通信（IPC）
•	管道/命名管道（同机父子/不相关进程）
•	消息队列
•	共享内存 + 信号量（最快，需同步）
•	Socket（跨主机/同机皆可，TCP/UDP）
•	还有：信号、内存映射文件（mmap）等。

⸻

场景题

如何设计一个“秒杀”系统（要点版）
1.	入口限流：网关/接口层令牌桶、滑动窗口；超限直接拒绝或排队。
2.	库存前置到 Redis：原子扣减（Lua 脚本 decr >= 0），避免超卖；设置售罄标记。
3.	异步削峰：下单请求落 MQ（Kafka/RabbitMQ），消费者异步创建订单。
4.	幂等：用户 + 商品 形成 唯一下单键（Redis SETNX / 去重表 / 唯一索引）；重试不重复下单。
5.	数据库落单：最终一致性；本地事务 + 业务流水；失败补偿/重放。
6.	热点防击穿：本地缓存 + Redis + 预热；过期随机化；布隆过滤器拦截无效商品。
7.	安全：签名/时戳/验证码/风控（设备/频率/黑名单）。
8.	监控 & 灰度：QPS、失败率、库存一致性校验，逐步放量。

关键三件套：限流、缓存+原子扣减、MQ削峰；再加 幂等 和 最终一致。