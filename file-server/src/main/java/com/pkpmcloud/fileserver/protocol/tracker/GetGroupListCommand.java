package com.pkpmcloud.fileserver.protocol.tracker;

import com.pkpmcloud.fileserver.model.GroupState;
import com.pkpmcloud.fileserver.protocol.tracker.request.GetGroupListRequest;
import com.pkpmcloud.fileserver.protocol.tracker.response.GetGroupListResponse;

import java.util.List;

/**
 * 获取Group信息命令
 * 作者：LiZW <br/>
 * 创建时间：2016/11/20 15:05 <br/>
 */
public class GetGroupListCommand extends TrackerCommand<List<GroupState>> {

    public GetGroupListCommand() {
        super.request = new GetGroupListRequest();
        super.response = new GetGroupListResponse();
    }
}
