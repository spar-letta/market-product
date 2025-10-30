package com.cmis.cooperative.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    public static final String DIRECT_EXCHANGE = "direct-exchange";
    public static final String AUDIT_QUEUE = "audit-queue";

    public static final String ROUTING_KEY_AUDIT = "audit";

    public static final String NAME_RESERVATION_APPROVAL_QUEUE = "name-reservation-approval";
    public static final String ROUTING_KEY_NAME_RESERVATION_APPROVAL_QUEUE = "key_name-reservation-approval";

    public static final String NAME_APPROVAL_RESPONSE_QUEUE = "name-approval-response-queue";
    public static final String ROUTING_KEY_NAME_APPROVAL_RESPONSE_QUEUE = "key_name-approval-response";

    public static final String SOCIETY_APPROVAL_QUEUE = "society-approval";
    public static final String ROUTING_KEY_SOCIETY_APPROVAL_QUEUE = "key-society-approval";

    public static final String SOCIETY_APPROVAL_EVENT_QUEUE = "society-approval_event";
    public static final String ROUTING_KEY_SOCIETY_APPROVAL_EVENT_QUEUE = "key-society-approval_event";

    public static final String MARK_SOCIETY_FILES_EVENT_QUEUE = "mark_society_files_event";
    public static final String ROUTING_KEY_MARK_SOCIETY_FILES_EVENT_QUEUE = "key_mark_society_files_event";

    public static final String AUDIT_EVENT_QUEUE = "audit_event_queue";
    public static final String ROUTING_KEY_AUDIT_EVENT_QUEUE = "key_audit_event_queue";

    public static final String ASSIGN_MESSAGE_EVENT_QUEUE = "assign_message_event_queue";
    public static final String ROUTING_KEY_ASSIGN_MESSAGE_EVENT_QUEUE = "key_assign_message_event_queue";

    public static final String SOCIETY_AMENDMENT_APPROVAL_QUEUE = "society_amendment_approval_queue";
    public static final String ROUTING_KEY_SOCIETY_AMENDMENT_APPROVAL_QUEUE = "key_society_amendment_approval";

    public static final String SOCIETY_AMENDMENT_APPROVAL_RETURN_EVENT_QUEUE = "society_amendment_approval_return_event_queue";
    public static final String ROUTING_KEY_SOCIETY_AMENDMENT_APPROVAL_RETURN_EVENT_QUEUE = "key_society_amendment_approval_return_event_queue";

    public static final String PROFORMA_MESSAGE_EVENT_QUEUE = "proforma_message_event_queue";
    public static final String ROUTING_KEY_PROFORMA_MESSAGE_EVENT_QUEUE = "key_proforma_message_event_queue";

    public static final String PROFORMA_MESSAGE_RETURN_EVENT_QUEUE = "proforma_message_return_event_queue";
    public static final String ROUTING_KEY_PROFORMA_MESSAGE_RETURN_EVENT_QUEUE = "key_proforma_message_return_event_queue";

    public static final String USER_MESSAGE_EVENT_QUEUE = "user_message_event_queue";
    public static final String ROUTING_KEY_USER_MESSAGE_EVENT_QUEUE = "key_user_message_event_queue";

    public static final String USER_ASSIGNED_ALERT_QUEUE = "user_assigned_alert_queue";
    public static final String ROUTING_KEY_USER_ASSIGNED_ALERT_QUEUE = "key_user_assigned_alert_queue";

    public static final String BORROWING_POWER_APPROVAL_QUEUE = "borrowing_power_approval";
    public static final String ROUTING_KEY_BORROWING_POWER_APPROVAL_QUEUE = "key_borrowing_power_approval";

    public static final String BORROWING_POWER_APPROVAL_RETURN_QUEUE = "return_borrowing_power_approval";
    public static final String ROUTING_KEY_BORROWING_POWER_APPROVAL_RETURN_QUEUE = "key_return_borrowing_power_approval";

    public static final String WEALTH_DECLARATION_APPROVAL_QUEUE = "wealth_declaration_approval";
    public static final String ROUTING_KEY_WEALTH_DECLARATION_APPROVAL_QUEUE = "key_wealth_declaration_approval";

    public static final String WEALTH_DECLARATION_APPROVAL_RETURN_QUEUE = "wealth_declaration_return_approval";
    public static final String ROUTING_KEY_WEALTH_DECLARATION_APPROVAL_RETURN_QUEUE = "key_wealth_declaration_return_approval";

    public static final String OFFICIAL_SEARCH_APPROVAL_QUEUE = "official_search_approval";
    public static final String ROUTING_KEY_OFFICIAL_SEARCH_APPROVAL_QUEUE = "key_official_search_approval";

    public static final String OFFICIAL_SEARCH_APPROVAL_RETURN_QUEUE = "official_search_return_approval";
    public static final String ROUTING_KEY_OFFICIAL_SEARCH_APPROVAL_RETURN_QUEUE = "key_official_search_return_approval";

    public static final String ATTACH_FILE_QUEUE = "attach_file_queue";
    public static final String ROUTING_KEY_ATTACH_FILE_QUEUE = "key_attach_file_queue";

    public static final String AGENCY_NOTICE_QUEUE = "agency_notice_queue";
    public static final String ROUTING_KEY_AGENCY_NOTICE_QUEUE = "key_agency_notice_queue";

    public static final String AGENCY_NOTICE_RETURN_QUEUE = "agency_notice_return_queue";
    public static final String ROUTING_KEY_AGENCY_NOTICE_RETURN_QUEUE = "key_agency_notice_return_queue";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue auditQueue() {
        return new Queue(AUDIT_QUEUE);
    }

    @Bean
    public Queue nameReservedApprovalQueue() {
        return new Queue(NAME_RESERVATION_APPROVAL_QUEUE);
    }

    @Bean
    public Queue nameApprovalResponseQueue() {
        return new Queue(NAME_APPROVAL_RESPONSE_QUEUE);
    }

    @Bean
    public Queue societyApprovalQueue() {
        return new Queue(SOCIETY_APPROVAL_QUEUE);
    }

    @Bean
    public Queue societyApprovalEventQueue() {
        return new Queue(SOCIETY_APPROVAL_EVENT_QUEUE);
    }

    @Bean
    public Queue markSocietyFilesEventQueue() {
        return new Queue(MARK_SOCIETY_FILES_EVENT_QUEUE);
    }

    @Bean
    public Queue auditEventQueue() {
        return new Queue(AUDIT_EVENT_QUEUE);
    }

    @Bean
    public Queue assignMessageEventQueue() {
        return new Queue(ASSIGN_MESSAGE_EVENT_QUEUE);
    }

    @Bean
    public Queue societyAmendmentApprovalEventQueue() {
        return new Queue(SOCIETY_AMENDMENT_APPROVAL_QUEUE);
    }

    @Bean
    public Queue societyAmendmentReturnApprovalEventQueue() {
        return new Queue(SOCIETY_AMENDMENT_APPROVAL_RETURN_EVENT_QUEUE);
    }

    @Bean
    public Queue proformaMessageEventQueue() {
        return new Queue(PROFORMA_MESSAGE_EVENT_QUEUE);
    }

    @Bean
    public Queue proformaMessageReturnEventQueue() {
        return new Queue(PROFORMA_MESSAGE_RETURN_EVENT_QUEUE);
    }

    @Bean
    public Queue userMessageEventQueue() {
        return new Queue(USER_MESSAGE_EVENT_QUEUE);
    }

    @Bean
    public Queue userAssignedAlertQueue() {
        return new Queue(USER_ASSIGNED_ALERT_QUEUE);
    }

    @Bean
    public Queue borrowingPowerApprovalQueue() {
        return new Queue(BORROWING_POWER_APPROVAL_QUEUE);
    }

    @Bean
    public Queue borrowingPowerApprovalReturnQueue() {
        return new Queue(BORROWING_POWER_APPROVAL_RETURN_QUEUE);
    }

    @Bean
    public Queue wealthDeclarationApprovalQueue() {
        return new Queue(WEALTH_DECLARATION_APPROVAL_QUEUE);
    }

    @Bean
    public Queue wealthDeclarationApprovalReturnQueue() {
        return new Queue(WEALTH_DECLARATION_APPROVAL_RETURN_QUEUE);
    }

    @Bean
    public Queue officialSearchApprovalQueue() {
        return new Queue(OFFICIAL_SEARCH_APPROVAL_QUEUE);
    }

    @Bean
    public Queue officialSearchApprovalReturnQueue() {
        return new Queue(OFFICIAL_SEARCH_APPROVAL_RETURN_QUEUE);
    }

    @Bean
    public Queue attachFileQueue() {
        return new Queue(ATTACH_FILE_QUEUE);
    }

    @Bean
    public Queue agencyNoticeQueue() {
        return new Queue(AGENCY_NOTICE_QUEUE);
    }

    @Bean
    public Queue agencyNoticeReturnQueue() {
        return new Queue(AGENCY_NOTICE_RETURN_QUEUE);
    }

    @Bean
    public Binding reportBinding() {
        return BindingBuilder.bind(auditQueue()).to(directExchange()).with(ROUTING_KEY_AUDIT);
    }

    @Bean
    public Binding nameReservedApprovalBinding() {
        return BindingBuilder.bind(nameReservedApprovalQueue()).to(directExchange()).with(ROUTING_KEY_NAME_RESERVATION_APPROVAL_QUEUE);
    }

    @Bean
    public Binding nameApprovalResponseBinding() {
        return BindingBuilder.bind(nameApprovalResponseQueue()).to(directExchange()).with(ROUTING_KEY_NAME_APPROVAL_RESPONSE_QUEUE);
    }

    @Bean
    public Binding societyApprovalBinding() {
        return BindingBuilder.bind(societyApprovalQueue()).to(directExchange()).with(ROUTING_KEY_SOCIETY_APPROVAL_QUEUE);
    }

    @Bean
    public Binding societyApprovalEventBinding() {
        return BindingBuilder.bind(societyApprovalEventQueue()).to(directExchange()).with(ROUTING_KEY_SOCIETY_APPROVAL_EVENT_QUEUE);
    }

    @Bean
    public Binding markSocietyFilesEventBinding() {
        return BindingBuilder.bind(markSocietyFilesEventQueue()).to(directExchange()).with(ROUTING_KEY_MARK_SOCIETY_FILES_EVENT_QUEUE);
    }

    @Bean
    public Binding auditEventBinding() {
        return BindingBuilder.bind(auditEventQueue()).to(directExchange()).with(ROUTING_KEY_AUDIT_EVENT_QUEUE);
    }

    @Bean
    public Binding assignMessageEventBinding() {
        return BindingBuilder.bind(assignMessageEventQueue()).to(directExchange()).with(ROUTING_KEY_ASSIGN_MESSAGE_EVENT_QUEUE);
    }

    @Bean
    public Binding societyAmendmentEventBinding() {
        return BindingBuilder.bind(societyAmendmentApprovalEventQueue()).to(directExchange()).with(ROUTING_KEY_SOCIETY_AMENDMENT_APPROVAL_QUEUE);
    }

    @Bean
    public Binding societyAmendmentReturnEventBinding() {
        return BindingBuilder.bind(societyAmendmentReturnApprovalEventQueue()).to(directExchange()).with(ROUTING_KEY_SOCIETY_AMENDMENT_APPROVAL_RETURN_EVENT_QUEUE);
    }

    @Bean
    public Binding proformaMessageEventBinding() {
        return BindingBuilder.bind(proformaMessageEventQueue()).to(directExchange()).with(ROUTING_KEY_PROFORMA_MESSAGE_EVENT_QUEUE);
    }

    @Bean
    public Binding proformaMessageReturnEventBinding() {
        return BindingBuilder.bind(proformaMessageReturnEventQueue()).to(directExchange()).with(ROUTING_KEY_PROFORMA_MESSAGE_RETURN_EVENT_QUEUE);
    }

    @Bean
    public Binding userMessageEventBinding() {
        return BindingBuilder.bind(userMessageEventQueue()).to(directExchange()).with(ROUTING_KEY_USER_MESSAGE_EVENT_QUEUE);
    }

    @Bean
    public Binding userAssignedAlertBinding() {
        return BindingBuilder.bind(userAssignedAlertQueue()).to(directExchange()).with(ROUTING_KEY_USER_ASSIGNED_ALERT_QUEUE);
    }

    @Bean
    public Binding borrowingPowerApprovalBinding() {
        return BindingBuilder.bind(borrowingPowerApprovalQueue()).to(directExchange()).with(ROUTING_KEY_BORROWING_POWER_APPROVAL_QUEUE);
    }

    @Bean
    public Binding borrowingPowerApprovalReturnBinding() {
        return BindingBuilder.bind(borrowingPowerApprovalReturnQueue()).to(directExchange()).with(ROUTING_KEY_BORROWING_POWER_APPROVAL_RETURN_QUEUE);
    }

    @Bean
    public Binding wealthDeclarationApprovalBinding() {
        return BindingBuilder.bind(wealthDeclarationApprovalQueue()).to(directExchange()).with(ROUTING_KEY_WEALTH_DECLARATION_APPROVAL_QUEUE);
    }

    @Bean
    public Binding wealthDeclarationApprovalReturnBinding() {
        return BindingBuilder.bind(wealthDeclarationApprovalReturnQueue()).to(directExchange()).with(ROUTING_KEY_WEALTH_DECLARATION_APPROVAL_RETURN_QUEUE);
    }

    @Bean
    public Binding officialSearchApprovalBinding() {
        return BindingBuilder.bind(officialSearchApprovalQueue()).to(directExchange()).with(ROUTING_KEY_OFFICIAL_SEARCH_APPROVAL_QUEUE);
    }

    @Bean
    public Binding officialSearchApprovalReturnBinding() {
        return BindingBuilder.bind(officialSearchApprovalReturnQueue()).to(directExchange()).with(ROUTING_KEY_OFFICIAL_SEARCH_APPROVAL_RETURN_QUEUE);
    }

    @Bean
    public Binding attachFileBinding() {
        return BindingBuilder.bind(attachFileQueue()).to(directExchange()).with(ROUTING_KEY_ATTACH_FILE_QUEUE);
    }

    @Bean
    public Binding agencyNoticeBinding() {
        return BindingBuilder.bind(agencyNoticeQueue()).to(directExchange()).with(ROUTING_KEY_AGENCY_NOTICE_QUEUE);
    }

    @Bean
    public Binding agencyNoticeReturnBinding() {
        return BindingBuilder.bind(agencyNoticeReturnQueue()).to(directExchange()).with(ROUTING_KEY_AGENCY_NOTICE_RETURN_QUEUE);
    }
}
