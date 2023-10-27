package cn.iocoder.yudao.service.service.infra.data;

import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeExportInput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeListAllSimpleOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeGetOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypePageOutput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypePageInput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeUpdateInput;
import cn.iocoder.yudao.service.vo.infra.data.dictType.DictTypeCreateInput;
import java.util.*;
import cn.iocoder.yudao.service.vo.infra.data.dictType.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.servlet.http.HttpServletResponse;

/**
 * 字典类型 Service 接口
 */
public interface DictTypeService {

    UUID create(DictTypeCreateInput inputVO);

    Boolean update(DictTypeUpdateInput inputVO);

    Boolean delete(UUID id);

    PageResult<DictTypePageOutput> page(DictTypePageInput inputVO);

    DictTypeGetOutput get(UUID id);

    List<DictTypeListAllSimpleOutput> listAllSimple();

    void export(HttpServletResponse response, DictTypeExportInput inputVO);

}
