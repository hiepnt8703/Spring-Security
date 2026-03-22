package common;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;


}
