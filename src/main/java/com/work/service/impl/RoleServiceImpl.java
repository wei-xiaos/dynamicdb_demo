package com.work.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.work.common.Response;
import com.work.common.Result;
import com.work.common.enums.StatusCode;
import com.work.common.enums.utils.ListUtil;
import com.work.entity.CreateTableRecord;
import com.work.entity.Role;
import com.work.entity.Tables;
import com.work.mapper.RoleMapper;
import com.work.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Value("${spring.datasource.url}")
    private String url;

    @Override
    public Response addRole(Role role) {
        int flag = roleMapper.exists(role);
        if (flag > 0) {
            return new Response(StatusCode.EXISTS.getCode(), StatusCode.EXISTS.getMsg());
        }
        int result = roleMapper.addRole(role);
        return Result.check(result);
    }

    @Override
    public Response updateRole(Role role) {
        int result = roleMapper.updateRole(role);
        return Result.check(result);
    }

    @Override
    public Response deleteRole(Role role) {
        int result = roleMapper.deleteRole(role);
        return Result.check(result);
    }

    @Override
    public Response queryRoleInfo(Role role, Integer page, Integer limit) {
        Map map = new HashMap<>(3);
        Page page1 = PageHelper.startPage(page, limit);
        List<Role> result = roleMapper.queryRoleInfo(role);
        map.put("total", page1.getTotal());
        map.put("data", result);

        return new Response(0, result, "success");
    }

    /**
     * 验证表与属性是否存在
     * @param tables
     * @return
     */
    private boolean checkTableData(Tables tables){
        String path = url.substring(url.lastIndexOf("/")+1, url.indexOf("?"));
        int count = roleMapper.existsTable(path, tables.getTableName());
        if (count >= 1) {
            return false;
        }
        //判断属性是否重复
        if (!ListUtil.listFieldRepeat(tables.getList())) {
            return false;
        }
        return true;
    }

    /**
     * 添加创建表记录
     * @param tables
     * @return
     */
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public Response createTableRecord(Tables tables) {
        if(!checkTableData(tables)){
            return new Response(StatusCode.EXISTS.getCode(), StatusCode.EXISTS.getMsg());
        }
        int a = roleMapper.newTableRecord(JSONObject.toJSONString(tables));
        if (a > 0) {
            return new Response();
        }
        return null;
    }

    /**
     * 是否同步建表
     * @param createTableRecord
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public Response synTableAndField(CreateTableRecord createTableRecord) {
        String tableData = roleMapper.queryTableRecord(createTableRecord.getId());
        Integer type = roleMapper.isNewData(createTableRecord.getId());
        Tables tables = JSONObject.parseObject(tableData, Tables.class);
        if(!checkTableData(tables)){
            return new Response(StatusCode.EXISTS.getCode(), StatusCode.EXISTS.getMsg());
        }
        createTableRecord.setStatus(2);
        roleMapper.updateTableRecord(createTableRecord);
        tables.setIsSyn(createTableRecord.getStatus());
        int a = roleMapper.synTableAndField(tables);
        if (a > 0) {
            return new Response();
        }
        return null;
    }
}
