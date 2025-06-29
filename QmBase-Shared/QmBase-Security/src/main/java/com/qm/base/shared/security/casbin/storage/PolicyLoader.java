package com.qm.base.shared.security.casbin.storage;

import java.util.List;

public interface PolicyLoader {
    List<List<String>> loadPolicies();
}
