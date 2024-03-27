package com.pizzeria.munayco.controller;

import com.pizzeria.munayco.aggregates.request.RequestMessage;
import com.pizzeria.munayco.aggregates.response.ResponseBase;
import com.pizzeria.munayco.service.MessagesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/message")
public class MessagesController {
    private final MessagesService messagesService;

    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/create")
    public ResponseBase createMessage(@RequestBody RequestMessage requestMessage) {
        return messagesService.createMessage(requestMessage);
    }

    @DeleteMapping("{id}")
    public ResponseBase deleteMessage(@PathVariable int id) {
        return messagesService.deleteMessageById(id);
    }

    @GetMapping("/all")
    public ResponseBase findAllMessages() {
        return messagesService.findAllMessages();
    }

    @GetMapping("{id}")
    public ResponseBase findOneMessage(@PathVariable int id) {
        return messagesService.findMessageById(id);
    }

    @PatchMapping("{id}")
    public ResponseBase updateMessage(@PathVariable int id, @RequestBody RequestMessage requestMessage) {
        return messagesService.updateMessageById(id, requestMessage);
    }
}
