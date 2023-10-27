package cn.iocoder.yudao.service.vo.system.user.baseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 用户信息表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class UserBase {

    @Schema(description = "用户账号", requiredMode = Schema.RequiredMode.REQUIRED, example = "yudao")
    private String username;

    @Schema(description = "密码", example = "2132")
    private String password;

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    private String nickname;

    @Schema(description = "备注", example = "我是一个用户")
    private String remark;

    @Schema(description = "部门ID", example = "1")
    private Long deptId;

    @Schema(description = "岗位编号数组", example = "[1]")
    private List<Long> postIds;

    @Schema(description = "用户邮箱", example = "yudao@iocoder.cn")
    private String email;

    @Schema(description = "手机号码", example = "15601691300")
    private String mobile;

    @Schema(description = "用户性别", example = "1")
    private Integer sex;

    @Schema(description = "用户头像", example = "https://www.iocoder.cn/xxx.png")
    private String avatar;

    @Schema(description = "帐号状态（0正常 1停用）", example = "0")
    private Integer status;

    @Schema(description = "最后登录IP", example = "232.342.343.23")
    private String loginIp;

    @Schema(description = "最后登录时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime loginDate;

}
