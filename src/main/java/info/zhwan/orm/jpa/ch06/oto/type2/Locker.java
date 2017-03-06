package info.zhwan.orm.jpa.ch06.oto.type2;

import lombok.Getter;

import javax.persistence.*;

/**
 * @author zhwan
 */
@Entity
@Getter
public class Locker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToOne
  @JoinColumn(name = "MEMBER_ID", unique = true)
  private Member member;

  public Locker(String name) {
    this.name = name;
  }

  public void setMember(Member member) {
    this.member = member;
    if (this.member.getLocker() == null) {
      this.member.setLocker(this);
    }
  }
}
