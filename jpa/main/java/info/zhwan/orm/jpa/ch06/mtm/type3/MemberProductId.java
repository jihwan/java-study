package info.zhwan.orm.jpa.ch06.mtm.type3;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author zhwan
 */
@Embeddable @Getter
public class MemberProductId implements Serializable {
  private Long member;
  private Long product;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MemberProductId that = (MemberProductId) o;

    if (!getMember().equals(that.getMember())) return false;
    return getProduct().equals(that.getProduct());
  }

  @Override
  public int hashCode() {
    int result = getMember().hashCode();
    result = 31 * result + getProduct().hashCode();
    return result;
  }
}
