package com.example.projectCommunity.DTOs.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
    private long id;
    private Set<UserDTO> participants;
    private List<MessageDTO> messages;
}
