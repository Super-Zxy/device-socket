package com.device.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

@Data
public class ServerParamVo implements Serializable {

	private static final long serialVersionUID = 5267331270045085979L;

	private String userId;

	private String message;
}
