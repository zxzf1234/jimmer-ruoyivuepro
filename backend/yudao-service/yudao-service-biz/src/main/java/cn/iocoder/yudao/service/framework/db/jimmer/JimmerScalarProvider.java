
package cn.iocoder.yudao.service.framework.db.jimmer;
import cn.iocoder.yudao.framework.file.core.client.FileClientConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.babyfish.jimmer.sql.runtime.ScalarProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


@Configuration
public class JimmerScalarProvider {
    @Bean
    public ScalarProvider FileClientConfigScalarProvider() {
        return new ScalarProvider<FileClientConfig, String>(
        ) {

            public FileClientConfig toScalar(String sqlValue) throws JsonProcessingException {
                return new ObjectMapper().readValue(sqlValue, FileClientConfig.class);
            }

            public String toSql(FileClientConfig scalarValue) throws JsonProcessingException {
                return new ObjectMapper().writeValueAsString(scalarValue);
            }

        };
    }

    @Bean
    public ScalarProvider UUIDScalarProvider() {
        return new ScalarProvider<UUID, String>(
        ) {

            public UUID toScalar(String sqlValue) {
                return UUID.fromString(sqlValue);
            }

            public String toSql(UUID scalarValue) {
                return scalarValue.toString();
            }

        };
    }
}

