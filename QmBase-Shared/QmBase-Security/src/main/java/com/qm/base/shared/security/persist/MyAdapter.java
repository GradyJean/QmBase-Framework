package com.qm.base.shared.security.persist;

import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;

import java.util.List;

public class MyAdapter implements Adapter {
    @Override
    public void loadPolicy(Model model) {

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
