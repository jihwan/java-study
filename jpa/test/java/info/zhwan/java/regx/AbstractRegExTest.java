package info.zhwan.java.regx;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;

/**
 * Created by zhwan on 17. 1. 20.
 */
@Slf4j
abstract class AbstractRegExTest {
  protected int findResolver(Matcher m) {
    log.info("==================================================================================");
    int findCount = 0;
    while (m.find()) {
      // loop 내부에서 lookingAt() 메소드 호출 금지
      log.info("NO:{}, group:{}, groupCount:{}, start:{}",
          findCount++ + 1,
          m.group(),
          m.groupCount(),
          m.start()
      );

      for (int i = 0; i <m.groupCount(); i++) {
        log.info("group {} : {}", i + 1, m.group(i + 1));
      }
    }
    return findCount;
  }
}
