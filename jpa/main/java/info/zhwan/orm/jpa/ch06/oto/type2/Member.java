package info.zhwan.orm.jpa.ch06.oto.type2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zhwan
 */
@Entity
@Getter
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToOne(mappedBy = "member")
  private Locker locker;

  public Member(String name) {
    this.name = name;
  }

  public void setLocker(Locker locker) {
    this.locker = locker;
    if (this.locker.getMember() == null) {
      this.locker.setMember(this);
    }
  }
}
