//package com.cmis.cooperative.events;
//
//import com.cmis.cooperative.config.EventConfig;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class EventConfigTest {
//
//    @Autowired
//    private DirectExchange directExchange;
//
//    @Autowired
//    private Queue auditQueue;
//
//    @Autowired
//    private Queue nameReservedApprovalQueue;
//
//    @Autowired
//    private Queue nameApprovalResponseQueue;
//
//    @Autowired
//    private Binding reportBinding;
//
//    @Autowired
//    private Binding nameReservedApprovalBinding;
//
//    private Binding nameApprovalResponseBinding;
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @Test
//    void testExchangeExists() {
//        assertThat(directExchange).isNotNull();
//        assertThat(directExchange.getName()).isEqualTo(EventConfig.DIRECT_EXCHANGE);
//    }
//
//    @Test
//    void testQueuesExist() {
//        assertThat(auditQueue.getName()).isEqualTo(EventConfig.AUDIT_QUEUE);
//        assertThat(nameReservedApprovalQueue.getName()).isEqualTo(EventConfig.NAME_RESERVATION_APPROVAL_QUEUE);
//        assertThat(nameApprovalResponseQueue.getName()).isEqualTo(EventConfig.NAME_APPROVAL_RESPONSE_QUEUE);
//    }
//
//    @Test
//    void testBindingsExist() {
//        assertThat(reportBinding.getRoutingKey()).isEqualTo(EventConfig.ROUTING_KEY_AUDIT);
//        assertThat(nameReservedApprovalBinding.getRoutingKey()).isEqualTo(EventConfig.ROUTING_KEY_NAME_RESERVATION_APPROVAL_QUEUE);
//        assertThat(nameApprovalResponseBinding.getRoutingKey()).isEqualTo(EventConfig.ROUTING_KEY_NAME_APPROVAL_RESPONSE_QUEUE);
//    }
//
//    @Test
//    void testRabbitTemplateConfigured() {
//        assertThat(rabbitTemplate).isNotNull();
//        assertThat(rabbitTemplate.getMessageConverter()).isInstanceOf(org.springframework.amqp.support.converter.Jackson2JsonMessageConverter.class);
//    }
//}
