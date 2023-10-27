package cn.iocoder.yudao.service.service.infra.oauth2;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Approve;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2ApproveDraft;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Client;
import cn.iocoder.yudao.service.repository.infra.oauth2.SystemOauth2ApproveRepository;
import cn.iocoder.yudao.service.repository.infra.oauth2.SystemOauth2ClientRepository;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;

/**
 * OAuth2 批准 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OAuth2ApproveServiceImpl implements OAuth2ApproveService {

    /**
     * 批准的过期时间，默认 30 天
     */
    private static final Integer TIMEOUT = 30 * 24 * 60 * 60; // 单位：秒

    @Resource
    private SystemOauth2ClientRepository systemOauth2ClientRepository;

    @Resource
    private SystemOauth2ApproveRepository systemOauth2ApproveRepository;

    @Override
    @Transactional
    public boolean checkForPreApproval(Long userId, Integer userType, String clientId, Collection<String> requestedScopes) {
        // 第一步，基于 Client 的自动授权计算，如果 scopes 都在自动授权中，则返回 true 通过
        Optional<SystemOauth2Client> opClient = systemOauth2ClientRepository.findByClientId(clientId);
        Assert.notNull(!opClient.isPresent(), "客户端不能为空"); // 防御性编程
        if (CollUtil.containsAll(opClient.get().autoApproveScopes(), requestedScopes)) {
            // gh-877 - if all scopes are auto approved, approvals still need to be added to the approval store.
            LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
            for (String scope : requestedScopes) {
                saveApprove(userId, userType, clientId, scope, true, expireTime);
            }
            return true;
        }

        // 第二步，算上用户已经批准的授权。如果 scopes 都包含，则返回 true
        List<SystemOauth2Approve> approveDOs = getApproveList(userId, userType, clientId);
        Set<String> scopes = convertSet(approveDOs, SystemOauth2Approve::scope,
                SystemOauth2Approve::approved); // 只保留未过期的 + 同意的
        return CollUtil.containsAll(scopes, requestedScopes);
    }

    @Override
    @Transactional
    public boolean updateAfterApproval(Long userId, Integer userType, String clientId, Map<String, Boolean> requestedScopes) {
        // 如果 requestedScopes 为空，说明没有要求，则返回 true 通过
        if (CollUtil.isEmpty(requestedScopes)) {
            return true;
        }

        // 更新批准的信息
        boolean success = false; // 需要至少有一个同意
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
        for (Map.Entry<String, Boolean> entry : requestedScopes.entrySet()) {
            if (entry.getValue()) {
                success = true;
            }
            saveApprove(userId, userType, clientId, entry.getKey(), entry.getValue(), expireTime);
        }
        return success;
    }

    @Override
    public List<SystemOauth2Approve> getApproveList(Long userId, Integer userType, String clientId) {
        List<SystemOauth2Approve> approveDOs = systemOauth2ApproveRepository.findByUserIdAndUserTypeAndClientId(
                userId, userType, clientId);
        approveDOs.removeIf(o -> DateUtils.isExpired(o.expiresTime()));
        return approveDOs;
    }

    @VisibleForTesting
    void saveApprove(Long userId, Integer userType, String clientId,
                     String scope, Boolean approved, LocalDateTime expireTime) {
        // 先更新
        SystemOauth2Approve approveDO = SystemOauth2ApproveDraft.$.produce(SystemOauth2Approve->{
            SystemOauth2Approve.setUserId(userId).setUserType(userType)
                    .setClientId(clientId).setScope(scope).setApproved(approved).setExpiresTime(expireTime);
        });
//                new SystemOauth2Approve().setUserId(userId).setUserType(userType)
//                .setClientId(clientId).setScope(scope).setApproved(approved).setExpiresTime(expireTime);
//       不存在就更新
        systemOauth2ApproveRepository.upsert(approveDO);
    }

}
