package info.zhwan.guava.eventbus;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class EventBusTestMain implements CommandLineRunner {

  public static void main(String[] args) {
    try (ConfigurableApplicationContext context = SpringApplication.run(EventBusTestMain.class, args)) {
    }
  }

  @Bean
  EventBus eventBus() {
    return new EventBus();
  }

  @Autowired EventBus eventBus;

  @Override
  public void run(String... args) throws Exception {
    eventBus.post("This event will go to DeadEventProcessor because no subscriber");
    eventBus.post( new SendPostThourghClass ("This will go to subscribers"));
  }
}

@Component
@Slf4j
class DeadEventProcessor {

  @Autowired
  EventBus eventBus;

  @PostConstruct
  public void init(){
    eventBus.register(this);
  }

  @Subscribe
  public void processDeadEvent(DeadEvent deadEvent){
    log.error("DEADEVENT DETECTED: {}",deadEvent.getEvent().getClass());
  }
}

@Component
@Slf4j
class Subscriber1 {

  @Autowired
  EventBus eventBus;

  @PostConstruct
  public void init(){
    eventBus.register(this);
  }

  @Subscribe
  public void getPublishData(SendPostThourghClass sendPostThourghClass){
    log.info("Got post data on Subscriber1: {}", sendPostThourghClass.getName());
  }
}

@Component
@Slf4j
class Subscriber2 {

  @Autowired
  EventBus eventBus;

  @PostConstruct
  public void init(){
    eventBus.register(this);
  }

  @Subscribe
  public void getPublishData(SendPostThourghClass sendPostThourghClass){
    log.info("Got post data on Subscriber2: {}", sendPostThourghClass.getName());
  }
}

@Component
@Slf4j
class Subscriber3 {

  @Autowired
  EventBus eventBus;

  @PostConstruct
  public void init(){
    eventBus.register(this);
  }

  @Subscribe
  public void getPublishData(String msg){
    log.info("Got post data on Subscriber3: {}", msg);
  }
}

class SendPostThourghClass {

  private String name;

  public SendPostThourghClass(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}