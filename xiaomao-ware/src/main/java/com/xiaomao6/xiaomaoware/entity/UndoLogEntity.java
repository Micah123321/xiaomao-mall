package com.xiaomao6.xiaomaoware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
/**
 *
 *
 * @author micah
 * @email mxmicah@qq.com
 * @date 2021-09-15 23:15:41
 */
@Data
@Accessors(chain = true)
@TableName("undo_log")
public class UndoLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 *
	 */
	private Long branchId;
	/**
	 *
	 */
	private String xid;
	/**
	 *
	 */
	private String context;
	/**
	 *
	 */
	private byte[] rollbackInfo;
	/**
	 *
	 */
	private Integer logStatus;
	/**
	 *
	 */
	private Date logCreated;
	/**
	 *
	 */
	private Date logModified;
	/**
	 *
	 */
	private String ext;

}
