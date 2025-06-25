package com.qm.base.shared.security.persist;

import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.Helper;

import java.util.List;

public class SecurityAdapter implements Adapter {
    @Override
    public void loadPolicy(Model model) {
        String[] lines = {
                "p, alice, data1, read",
                "p, bob, data2, write",
                "g, alice, data1_admin"
        };
        for (String line : lines) {
            Helper.loadPolicyLine(line, model);
        }
    }


    @Override
    public void savePolicy(Model model) {

    }

    @Override
    public void addPolicy(String s, String s1, List<String> list) {

    }

    @Override
    public void removePolicy(String s, String s1, List<String> list) {

    }

    @Override
    public void removeFilteredPolicy(String s, String s1, int i, String... strings) {

    }
}
