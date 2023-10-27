package cn.iocoder.yudao.service.convert.infra.auth;

import cn.iocoder.yudao.framework.common.util.collection.CollectionUtils;
import cn.iocoder.yudao.service.api.system.sms.dto.code.SmsCodeSendReqDTO;
import cn.iocoder.yudao.service.api.system.sms.dto.code.SmsCodeUseReqDTO;
import cn.iocoder.yudao.service.api.system.social.dto.SocialUserBindReqDTO;
import cn.iocoder.yudao.service.model.infra.data.SystemMenu;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2AccessToken;
import cn.iocoder.yudao.service.model.system.permission.SystemRole;
import cn.iocoder.yudao.service.model.system.user.SystemUser;
import cn.iocoder.yudao.service.vo.infra.auth.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;

import java.util.*;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.filterList;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginRespVO convert(SystemOauth2AccessToken bean);

    String ID_ROOT = "";

    default AuthPermissionInfoRespVO convert(SystemUser user, List<SystemRole> roleList, List<SystemMenu> menuList) {
        return AuthPermissionInfoRespVO.builder()
            .user(AuthPermissionInfoRespVO.UserVO.builder().id(user.id()).nickname(user.nickname()).avatar(user.avatar()).build())
            .roles(CollectionUtils.convertSet(roleList, SystemRole::code))
            .permissions(CollectionUtils.convertSet(menuList, SystemMenu::permission))
            .build();
    }

    AuthMenuRespVO convertTreeNode(SystemMenu menu);

    /**
     * 将菜单列表，构建成菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    default List<AuthMenuRespVO> buildMenuTree(List<SystemMenu> menuList) {
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(SystemMenu::sort));
        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<UUID, AuthMenuRespVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.id(), AuthConvert.INSTANCE.convertTreeNode(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(ID_ROOT)).forEach(childNode -> {
            // 获得父节点
            AuthMenuRespVO parentNode = treeNodeMap.get(UUID.fromString(childNode.getParentId()));
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                    childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return filterList(treeNodeMap.values(), node -> ID_ROOT.equals(node.getParentId()));
    }

    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialLoginReqVO reqVO);

    SmsCodeSendReqDTO convert(AuthSmsSendReqVO reqVO);

    SmsCodeUseReqDTO convert(AuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

}
