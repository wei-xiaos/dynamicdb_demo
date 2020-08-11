package com.work.service;

import com.alibaba.fastjson.JSONObject;
import com.work.common.Response;
import com.work.entity.CreateTableRecord;
import com.work.entity.Role;
import com.work.entity.Tables;
import org.springframework.boot.jackson.JsonObjectDeserializer;


public interface IRoleService {
    Response addRole(Role role);

    Response updateRole(Role role);

    Response deleteRole(Role role);

    Response queryRoleInfo(Role role, Integer page, Integer limit);

    Response createTableRecord(Tables tables);


    Response synTableAndField(CreateTableRecord createTableRecord);
}
