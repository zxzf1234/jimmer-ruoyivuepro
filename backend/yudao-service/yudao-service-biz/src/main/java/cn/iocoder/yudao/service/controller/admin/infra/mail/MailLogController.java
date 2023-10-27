package cn.iocoder.yudao.service.controller.admin.infra.mail;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.service.vo.infra.mail.log.MailLogPageReqVO;
import cn.iocoder.yudao.service.vo.infra.mail.log.MailLogRespVO;
import cn.iocoder.yudao.service.convert.infra.mail.MailLogConvert;
import cn.iocoder.yudao.service.model.infra.mail.SystemMailLog;
import cn.iocoder.yudao.service.service.infra.mail.MailLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 邮件日志")
@RestController
@RequestMapping("/infra/mail-log")
public class MailLogController {

    @Resource
    private MailLogService mailLogService;

    @GetMapping("/page")
    @Operation(summary = "获得邮箱日志分页")
    @PreAuthorize("@ss.hasPermission('system:mail-log:query')")
    public CommonResult<PageResult<MailLogRespVO>> getMailLogPage(@Valid MailLogPageReqVO pageVO) {
        return success((mailLogService.getMailLogPage(pageVO)));
    }

    @GetMapping("/get")
    @Operation(summary = "获得邮箱日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:mail-log:query')")
    public CommonResult<MailLogRespVO> getMailTemplate(@RequestParam("id") Long id) {
        SystemMailLog mailLogDO = mailLogService.getMailLog(id);
        return success(MailLogConvert.INSTANCE.convert(mailLogDO));
    }

}
