package cn.iocoder.yudao.service.service.infra.codegen;

import cn.iocoder.yudao.service.vo.infra.codegen.interfaceModule.*;

import java.util.List;

/**
 * 接口模块 Service 接口
 */
public interface InterfaceModuleService {
    List<InterfaceModuleResp> getModuleList(InterfaceModuleListReqVO reqVO);

    List<InterfaceModuleSimpleRespVO> getSimpleModuleList();

    String create(InterfaceModuleCreateReq reqVO);

    String update(InterfaceModuleUpdateReq reqVO);

    InterfaceModuleResp getModule(String id);

    void deleteModule(String id);
}
