package cn.iocoder.yudao.service.convert.infra.sensitiveword;

import cn.iocoder.yudao.service.vo.infra.sensitiveword.SensitiveWordCreateReqVO;
import cn.iocoder.yudao.service.vo.infra.sensitiveword.SensitiveWordExcelVO;
import cn.iocoder.yudao.service.vo.infra.sensitiveword.SensitiveWordRespVO;
import cn.iocoder.yudao.service.vo.infra.sensitiveword.SensitiveWordUpdateReqVO;
import cn.iocoder.yudao.service.model.infra.sensitiveWord.SystemSensitiveWord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 敏感词 Convert
 *
 * @author 永不言败
 */
@Mapper
public interface SensitiveWordConvert {

    SensitiveWordConvert INSTANCE = Mappers.getMapper(SensitiveWordConvert.class);

    SystemSensitiveWord convert(SensitiveWordCreateReqVO bean);

    SystemSensitiveWord convert(SensitiveWordUpdateReqVO bean);

    SensitiveWordRespVO convert(SystemSensitiveWord bean);

    List<SensitiveWordRespVO> convertList(List<SystemSensitiveWord> list);

    List<SensitiveWordRespVO> convertPage(Page<SystemSensitiveWord> page);

    List<SensitiveWordExcelVO> convertList02(List<SystemSensitiveWord> list);

}
