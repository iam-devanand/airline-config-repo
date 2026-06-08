package com.deva.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Support {

    private String email;
    private String phone;
    private String hours;

}
