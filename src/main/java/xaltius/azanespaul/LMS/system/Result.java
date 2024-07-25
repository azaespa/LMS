package xaltius.azanespaul.LMS.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result {
    private String message;
    private int statusCode;
    private Object data;
}
