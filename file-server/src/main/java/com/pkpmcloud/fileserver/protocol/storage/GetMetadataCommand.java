package com.pkpmcloud.fileserver.protocol.storage;

import com.pkpmcloud.fileserver.model.MateData;
import com.pkpmcloud.fileserver.protocol.storage.request.GetMetadataRequest;
import com.pkpmcloud.fileserver.protocol.storage.response.GetMetadataResponse;

import java.util.Set;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 17:07 <br/>
 */
public class GetMetadataCommand extends StorageCommand<Set<MateData>> {

    /**
     * 设置文件标签(元数据)
     *
     * @param groupName 组名
     * @param path      文件路径
     */
    public GetMetadataCommand(String groupName, String path) {
        this.request = new GetMetadataRequest(groupName, path);
        // 输出响应
        this.response = new GetMetadataResponse();
    }
}
