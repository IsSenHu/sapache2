//package com.ssaw.smscenter.api;
//
//import com.ssaw.commons.util.json.jack.JsonUtils;
//import com.ssaw.smscenter.dao.entity.mo.NoteMo;
//import com.ssaw.smscenter.dao.repository.mo.NoteRepository;
//import com.ssaw.smscenter.model.message.MessageVO;
//import com.ssaw.smscenter.task.NoticeZhouYinPingJianSheYinHangKaTask;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.MessageSource;
//import org.springframework.context.MessageSourceAware;
//import org.springframework.data.annotation.Id;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Instant;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
///**
// * @author HuSen.
// * @date 2019/1/13 4:42.
// */
//@Slf4j
//@RestController
//public class Controller implements MessageSourceAware {
//
//    @Id
//    private String id;
//
//    private final NoticeZhouYinPingJianSheYinHangKaTask task;
//
//    private MessageSource messageSource;
//
//    private final NoteRepository noteRepository;
//
//    @Autowired
//    public Controller(NoticeZhouYinPingJianSheYinHangKaTask task, NoteRepository noteRepository) {
//        this.task = task;
//        this.noteRepository = noteRepository;
//    }
//
//    @GetMapping("/me/{me}/{stop}")
//    public Boolean change(@PathVariable(name = "me") Boolean me, @PathVariable(name = "stop") Boolean stop) {
//        task.me = me;
//        task.stop = stop;
//        log.info("信息通知人员我:{},启用:{}", me, stop);
//        return me;
//    }
//
//    @Override
//    public void setMessageSource(MessageSource messageSource) {
//        this.messageSource = messageSource;
//    }
//
//    @GetMapping("/me/sendMessage")
//    public SendResult send(MessageVO messageVO) {
//        log.info("messageSource:{}", messageSource);
//        NoteMo noteMo = new NoteMo();
//        noteMo.setId(UUID.randomUUID().toString());
//        noteMo.setOperateTime(Date.from(Instant.now()));
//        noteMo.setContext("测试");
//        noteRepository.save(noteMo);
//
//        List<NoteMo> all = noteRepository.findAll();
//        System.out.println(JsonUtils.object2JsonString(all));
//        return null;
//    }
//}
