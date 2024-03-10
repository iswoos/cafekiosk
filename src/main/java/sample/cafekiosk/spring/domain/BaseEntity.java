package sample.cafekiosk.spring.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
@MappedSuperclass:
> JPA 엔티티 클래스에 사용되는 어노테이션 중 하나.
이 어노테이션이 적용된 클래스는 테이블을 직접 매핑하지 않고, 자식 엔티티 클래스들이 상속할 공통 매핑 정보를 제공.
즉, BaseEntity는 테이블을 직접 가지지 않으며, 자식 클래스들이 이를 상속받아 사용.

@EntityListeners
JPA 엔티티 클래스에 사용. 엔티티의 생명주기 이벤트를 처리하기 위한 리스너를 지정하는 데 사용.
즉, 이 어노테이션을 사용하여 엔티티의 생성, 수정, 삭제 등의 이벤트를 감지하고 처리할 수 있는 리스너를 지정할 수 있음.

AuditingEntityListener.class
실제로 리스너 클래스를 가리킴.
AuditingEntityListener는 Spring Data JPA에서 제공하는 클래스 중 하나로, 엔티티의 생성 및 수정 시간을 추적하기 위한 기능을 구현.
이 클래스는 엔티티가 생성되거나 수정될 때 시간을 자동으로 업데이트하는 기능을 제공
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime modifiedDateTime;
}
