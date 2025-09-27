package org.example.friendshipapi.Models;

import lombok.Data;

@Data
public class RequestAnswer {

    private String sender;
    private String receiver;
    private String status;

}
