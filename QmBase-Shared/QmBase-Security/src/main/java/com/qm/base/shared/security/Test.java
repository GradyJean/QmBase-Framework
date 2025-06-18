package com.qm.base.shared.security;

import org.casbin.jcasbin.main.EnforceResult;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.util.EnforceContext;

public class Test {
    public static void main(String[] args) {
        String basePath = System.getProperty("user.dir");
        String casPath = basePath + "/QmBase-Shared/QmBase-Security/src/main/resources/";
        Enforcer enforcer = new Enforcer(casPath + "rbac.conf", casPath + "rbac_policy.csv");
        enforcer.enforce("alice", "data1", "read");
        EnforceContext enforceContext = new EnforceContext("2");
        // You can also specify a certain type individually
//        enforceContext.seteType("e");
        // Don't pass in EnforceContext; the default is r, p, e, m
        boolean res = enforcer.enforce("alice", "data2", "read");
        User user = new User();
        user.setAge(20);
        user.setName("alice");
        boolean res1 = enforcer.enforce(enforceContext, user, "/data1", "read");
        Adapter adapter = enforcer.getAdapter();
        System.out.println(res);
        EnforceResult result = enforcer.enforceEx(enforceContext, user, "/data1", "read");
        result.getExplain().forEach(System.out::println);
        System.out.println(res1);
    }

    public static class User {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
