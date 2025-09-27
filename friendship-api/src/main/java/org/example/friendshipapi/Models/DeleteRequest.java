package org.example.friendshipapi.Models;

import lombok.Data;

@Data
public class DeleteRequest {

    private String siteUser;
    private String friend;
}
