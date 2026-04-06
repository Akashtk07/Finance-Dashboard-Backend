package com.findash.util;

import java.util.List;

public class RoleValidator {
    public static void check(String role, List<String> allowed) {
        if (!allowed.contains(role)) {
            throw new RuntimeException("Access Denied");
        }
    }
}

