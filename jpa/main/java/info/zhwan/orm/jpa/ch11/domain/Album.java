package info.zhwan.orm.jpa.ch11.domain;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;

/**
 * @author zhwan
 */
@DiscriminatorValue("A")
@Getter
public class Album extends Item {
  private String artist;
}
