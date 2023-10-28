package persistence.entity.attribute.id;

import fixtures.TestEntityFixtures;
import jakarta.persistence.GenerationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import persistence.sql.infra.H2SqlConverter;

import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Nested
@DisplayName("LongTypeIdAttribute 클래스의")
class LongTypeIdAttributeTest {

    TestEntityFixtures.SampleOneWithValidAnnotation sample
            = new TestEntityFixtures.SampleOneWithValidAnnotation(1, "test_nick_name", 29);

    @Nested
    @DisplayName("of 메소드는")
    class of {
        @Nested
        @DisplayName("필드가 인자로 주어지면")
        class withValidArgs {
            @Test
            @DisplayName("LongTypeIdAttribute를 반환한다")
            void of() throws NoSuchFieldException {
                Field field = sample.getClass().getDeclaredField("id");
                LongTypeIdAttribute longTypeIdAttribute = new LongTypeIdAttribute(field);

                Assertions.assertAll(
                        () -> assertThat(longTypeIdAttribute.getFieldName()).isEqualTo("id"),
                        () -> assertThat(longTypeIdAttribute.getGenerationType()).isEqualTo(GenerationType.IDENTITY),
                        () -> assertThat(longTypeIdAttribute.getColumnName()).isEqualTo("id")
                );
            }
        }
    }

    @Nested
    @DisplayName("prepareDDL 메소드는")
    class prepareDDL {
        @Test
        @DisplayName("DDL을 반환한다")
        void prepareDDL() throws NoSuchFieldException {
            Field field = sample.getClass().getDeclaredField("id");

            LongTypeIdAttribute longTypeIdAttribute = new LongTypeIdAttribute(field);
            String ddl = longTypeIdAttribute.prepareDDL(new H2SqlConverter());

            assertThat(ddl).isEqualTo("id BIGINT GENERATED BY DEFAULT AS IDENTITY");
        }
    }
}
