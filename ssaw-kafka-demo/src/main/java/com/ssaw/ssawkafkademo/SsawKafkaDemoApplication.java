package com.ssaw.ssawkafkademo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author hszyp
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class SsawKafkaDemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(SsawKafkaDemoApplication.class, args);
    }

//    @Scheduled(fixedDelay = 1000)
//    public void send() {
//        String string = UUID.randomUUID().toString();
//        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("app_logs", string, string);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                log.info("消息发送失败:{}", throwable);
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, String> stringStringSendResult) {
//                log.info("消息发送成功:{}", stringStringSendResult.toString());
//            }
//        });
//    }
//
//    @KafkaListener(topics = "test_log", groupId = "app_logs", errorHandler = "ErrorHandlerImpl")
//    public void consumerA(ConsumerRecord<?, ?> consumer) {
//        log.info("1{}:{}-->{}:{}", consumer.topic(), consumer.partition(), consumer.key(), consumer.value());
//    }
//
//    @KafkaListener(topics = "app_logs", groupId = "app_logs", errorHandler = "ErrorHandlerImpl")
//    public void consumerB(ConsumerRecord<?, ?> consumer) {
//        log.info("2{}:{}-->{}:{}", consumer.topic(), consumer.partition(), consumer.key(), consumer.value());
//    }
//
//    @Component("ErrorHandlerImpl")
//    class ErrorHandlerImpl implements KafkaListenerErrorHandler {
//
//        @Override
//        public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
//            log.error("消息处理失败:{}", message);
//            return null;
//        }
//    }
}
