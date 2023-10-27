package cn.iocoder.yudao.service.service.infra.oauth2;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2Code;
import cn.iocoder.yudao.service.model.infra.oauth2.SystemOauth2CodeDraft;
import cn.iocoder.yudao.service.repository.infra.oauth2.SystemOauth2CodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.OAUTH2_CODE_EXPIRE;
import static cn.iocoder.yudao.service.enums.system.ErrorCodeConstants.OAUTH2_CODE_NOT_EXISTS;

/**
 * OAuth2.0 授权码 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class OAuth2CodeServiceImpl implements OAuth2CodeService {

    /**
     * 授权码的过期时间，默认 5 分钟
     */
    private static final Integer TIMEOUT = 5 * 60;

    @Resource
    private SystemOauth2CodeRepository systemOauth2CodeRepository;

    @Override
    public SystemOauth2Code createAuthorizationCode(Long userId, Integer userType, String clientId,
                                                List<String> scopes, String redirectUri, String state) {
        SystemOauth2Code codeDO = SystemOauth2CodeDraft.$.produce(draft -> {
            draft.setCode(generateCode()).setUserId(userId).setUserType(userType)
                    .setClientId(clientId).setScopes(scopes)
                    .setExpiresTime(LocalDateTime.now().plusSeconds(TIMEOUT))
                    .setRedirectUri(redirectUri).setState(state);
        });
        
        systemOauth2CodeRepository.insert(codeDO);
        return codeDO;
    }

    @Override
    public SystemOauth2Code consumeAuthorizationCode(String code) {
        Optional<SystemOauth2Code> opCodeDo = systemOauth2CodeRepository.findByCode(code);
        if (!opCodeDo.isPresent()) {
            throw exception(OAUTH2_CODE_NOT_EXISTS);
        }
        LocalDateTime expiresTime = opCodeDo.get().expiresTime();
        if (DateUtils.isExpired(expiresTime)) {
            throw exception(OAUTH2_CODE_EXPIRE);
        }
        systemOauth2CodeRepository.deleteById(opCodeDo.get().id());
        return opCodeDo.get();
    }

    private static String generateCode() {
        return IdUtil.fastSimpleUUID();
    }

}
