package com.device.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

@Data
public class ClientParamVo implements Serializable {

	private static final long serialVersionUID = 2822768619906469920L;

	private String userId;

	private String message;
}
