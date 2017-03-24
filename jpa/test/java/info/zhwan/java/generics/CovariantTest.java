package info.zhwan.java.generics;

import org.junit.Test;

/**
 * Created by zhwan on 17. 1. 6.
 */
public class CovariantTest {
  @Test
  public void test() {

    final Material material = new Lot();
//    final Lot lot = new Material();


//    final List<Lot> lots = new ArrayList<>();
//    final List<Material> materials = lots;


    final Lot[] lots = new Lot[]{};
    final Material[] materials = lots;

  }
}
