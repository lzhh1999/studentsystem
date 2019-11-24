package com.lhb.studentsystem.result;

import lombok.Data;

@Data
public class LayuiResponse {
    private int code = 0;
    private String msg = "";
    private long count;
    private Object data;
}
