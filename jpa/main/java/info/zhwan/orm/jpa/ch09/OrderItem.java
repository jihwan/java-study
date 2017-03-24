package info.zhwan.orm.jpa.ch09;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable @Getter
public class OrderItem implements Serializable {
  private String name;
  private int quantity = -1;

  protected OrderItem() {}

  public OrderItem(String name, int quantity) {
    this.name = name;

    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    OrderItem orderItem = (OrderItem) o;

    if (quantity != orderItem.quantity) return false;
    return name != null ? name.equals(orderItem.name) : orderItem.name == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + quantity;
    return result;
  }
}