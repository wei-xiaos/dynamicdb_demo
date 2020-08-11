package com.work.mapper;

import com.work.entity.CreateTableRecord;
import com.work.entity.Role;
import com.work.entity.Tables;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int addRole(Role role);

    int updateRole(Role role);

    int deleteRole(Role role);

    List<Role> queryRoleInfo(Role role);

    int exists(Role role);

    int newTableRecord(@Param("str") String str);

    int synTableAndField(Tables tables);

    String queryTableRecord(@Param("id")Long id);

    int updateTableRecord(CreateTableRecord createTableRecord);

    int existsTable(@Param("database")String database,@Param("tableName")String tableName);

    Integer isNewData(@Param("id") Long id);
}
