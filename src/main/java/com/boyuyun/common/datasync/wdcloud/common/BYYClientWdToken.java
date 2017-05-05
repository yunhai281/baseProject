package com.boyuyun.common.datasync.wdcloud.common;

import com.wdcloud.openapi.WdcloudToken;

/**
 * 伟东云Token继承类，实现获取超期的时间点，判断是否当前时间点Token超期
 * 
 * @date 2016-11-24 下午2:02:13
 */
public class BYYClientWdToken extends WdcloudToken {
	private static final long serialVersionUID = 6308938566560611321L;

	private long expiredAt; 	// 超期的时间点

	public BYYClientWdToken(WdcloudToken wdcloudToken) {
		this.setErrorMsg(wdcloudToken.getErrorMsg());
		this.setExpiresIn(wdcloudToken.getExpiresIn());
		this.setStatus(wdcloudToken.getStatus());
		this.setValue(wdcloudToken.getValue());
		this.expiredAt = System.currentTimeMillis() + wdcloudToken.getExpiresIn()*1000;//伟东的多少秒内超期是7200秒
		this.expiredAt = this.expiredAt - 1000*60;	//将超期时间提前一分钟，防止意外情况
	}

	public boolean isExpired() {
		return System.currentTimeMillis() > expiredAt;
	}
}
