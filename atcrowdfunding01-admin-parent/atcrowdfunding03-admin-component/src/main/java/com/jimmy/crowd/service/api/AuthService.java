package com.jimmy.crowd.service.api;

import com.jimmy.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    List<Auth> getAll();

    void saveRoleAuthorRelationship(Map<String, List<Integer>> map);
}
