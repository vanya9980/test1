package org.example.dispatcher;

import org.example.handler.UserRequestHandler;
import org.example.model.UserRequest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Dispatcher {
    private final List<UserRequestHandler> handlers;

    public Dispatcher(List<UserRequestHandler> handlers) {
        this.handlers = handlers;
    }

    public boolean dispatch(UserRequest userRequest) {
        for (UserRequestHandler userRequestHandler : handlers) {
            if(userRequestHandler.isApplicable(userRequest)){
                userRequestHandler.handle(userRequest);
                return true;
            }
        }
        return false;
    }
}
