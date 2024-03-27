package com.pizzeria.munayco.service;

import com.pizzeria.munayco.aggregates.request.RequestMessage;
import com.pizzeria.munayco.aggregates.response.ResponseBase;

public interface MessagesService {
    ResponseBase createMessage(RequestMessage requestMessage);
    ResponseBase deleteMessageById(Integer id);
    ResponseBase findMessageById(Integer id);
    ResponseBase findAllMessages();
    ResponseBase updateMessageById(Integer id, RequestMessage requestMessage);
}
