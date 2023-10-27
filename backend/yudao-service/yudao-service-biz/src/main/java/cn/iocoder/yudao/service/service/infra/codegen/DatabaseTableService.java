package cn.iocoder.yudao.service.service.infra.codegen;

import cn.iocoder.yudao.service.vo.infra.codegen.database.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.List;
import java.util.UUID;

public interface DatabaseTableService {

    List<DatabaseTableResp> getDatabaseTableList(DatabaseTableListReqVO listReqVO);

    /**
     * 获得表列表，基于表名称 + 表描述进行模糊匹配
     *
     * @param reqVo 新建的数据库表
     * @return 表列表
     */
    UUID createTable(DatabaseUpdateReq reqVo);

    /**
     * 获得表列表，基于表名称 + 表描述进行模糊匹配
     *
     * @param dataSourceConfigId 数据源配置的编号
     * @param nameLike 表名称，模糊匹配
     * @param commentLike 表描述，模糊匹配
     * @return 表列表
     */
    List<TableInfo> getTableList(Long dataSourceConfigId, String nameLike, String commentLike);

    /**
     * 获得指定表名
     *
     * @param dataSourceConfigId 数据源配置的编号
     * @param tableName 表名称
     * @return 表
     */
    TableInfo getTable(Long dataSourceConfigId, String tableName);

    /**
     * 获得数据库表详情
     *
     * @param tableId 数据库表id
     * @return 表详情
     */
    DatabaseTableDetailResp getDatabaseTable(UUID tableId);

    /**
     * 获得数据库表详情
     *
     * @param reqVO 更新的数据库表
     * @return 表详情
     */
    UUID updateTable(DatabaseUpdateReq reqVO);

    /**
     * 获得数据库表字段
     *
     * @param listReqVO 搜索
     * @return 字段详情
     */
    List<DatabaseTableColumnResp> getColumnList(DatabaseTableListReqVO listReqVO);
}
