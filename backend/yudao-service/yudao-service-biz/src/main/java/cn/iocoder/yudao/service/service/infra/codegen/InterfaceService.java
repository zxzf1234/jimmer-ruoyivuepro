package cn.iocoder.yudao.service.service.infra.codegen;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.*;

import java.util.List;
import java.util.UUID;

/**
 * 系统接口 Service 接口
 */
public interface InterfaceService {

    PageResult<InterfaceResp> getList(InterfaceListReqVO reqVO);

    String create(InterfaceEditInput reqVO);

    String update(InterfaceEditInput reqVO);

    InterfaceDetailResp getInterface(UUID id);

    List<InterfaceVoClassOutput> getVoClassList(InterfaceListReqVO reqVO);
}
