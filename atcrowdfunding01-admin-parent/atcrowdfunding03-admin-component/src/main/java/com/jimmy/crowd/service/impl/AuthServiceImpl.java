package com.jimmy.crowd.service.impl;

import com.jimmy.crowd.entity.Auth;
import com.jimmy.crowd.entity.AuthExample;
import com.jimmy.crowd.mapper.AuthMapper;
import com.jimmy.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public void saveRoleAuthorRelationship(Map<String, List<Integer>> map) {
        List<Integer> roleList = map.get("roleId");
        Integer roleId = roleList.get(0);

        authMapper.deleteOldRelationship(roleId);

        List<Integer> authIdList = map.get("authIdArray");
        if (authIdList != null && authIdList.size() > 0) {
            authMapper.insertNewRelationship(roleId,authIdList);
        }
    }
}
