package com.practice.servicedesc.web.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.servicedesc.entity.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {

    private Long id;
    private String userName;
    @NotBlank
    @Length(max = 512, message = "Description length must be smaller than 512 symbols.")
    @Pattern(regexp = "^(?!.*[<>]).*$", message = "Description must not contain HTML or script tags.")
    private String description;
    @NotBlank
    @Length(max = 32, message = "Value is too big.")
    @Pattern(regexp = "^(?!.*[<>]).*$", message = "Description must not contain HTML or script tags.")
    private String pcNameOrIp;
    private TicketStatus status;
    private Boolean isClosed;
    private Boolean isOverdue;
    private TicketWorkDto ticketWork;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime closedAt;
}
