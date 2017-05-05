package com.boyuyun.common.datasync;

public interface DataSyncHandler {
	public String getSystemName();
	public void handler(SyncDataType dtype,SyncOperateType otype,Object [] obj);
}
