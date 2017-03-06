package info.zhwan.orm.jpa.ch04;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
  name = "NAME_AGE_UNIQUE",
  columnNames = {"NAME", "AGE"})})
@Getter
@Setter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gen")
  @SequenceGenerator(name = "member_seq_gen", sequenceName = "member_seq")
  @Column(name = "ID")
  private Long id;


  @Column(name = "NAME", nullable = false, length = 10)
  private String username;

  private int age = -1;

  @Enumerated(EnumType.STRING)
  private RoleType roleType = RoleType.USER;

//  @Temporal(TemporalType.TIMESTAMP)
//  private Date createdDate;
  @Column(updatable = false)
  LocalDateTime createDateTime = LocalDateTime.now();

//  @Temporal(TemporalType.TIMESTAMP)
//  private Date lastModifiedDate;
  LocalDateTime lastModifiedDateTime = LocalDateTime.now();

  @Lob
  private String description;

//  @Transient
  private transient String temp;

  @Column(precision = 10, scale = 4)
  private BigDecimal cal;

  public Member(String username) {
    this.username = username;
  }

  public Member(String username, int age) {
    this.username = username;
    this.age = age;
  }
}