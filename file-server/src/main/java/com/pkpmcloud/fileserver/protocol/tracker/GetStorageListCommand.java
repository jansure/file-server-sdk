package com.pkpmcloud.fileserver.protocol.tracker;

import com.pkpmcloud.fileserver.model.StorageState;
import com.pkpmcloud.fileserver.protocol.tracker.request.GetListStorageRequest;
import com.pkpmcloud.fileserver.protocol.tracker.response.GetListStorageResponse;

import java.util.List;

/**
 * 获取Storage服务器状态命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 12:41 <br/>
 */
public class GetStorageListCommand extends TrackerCommand<List<StorageState>> {

    public GetStorageListCommand(String groupName, String storageIpAddr) {
        super.request = new GetListStorageRequest(groupName, storageIpAddr);
        super.response = new GetListStorageResponse();
    }

    public GetStorageListCommand(String groupName) {
        super.request = new GetListStorageRequest(groupName);
        super.response = new GetListStorageResponse();
    }
}
