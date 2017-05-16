namespace java com.qingju.java.mpns.service.thrift

service PayThriftService {

    string getConnectServerList();
    i64 getOnlineUserNum(1: string ip);

}

service PushThriftService {

    bool notify(1:string messagePushThrift);
    void reciveMessage(1:string messageReceiveThrift);

}

struct MessagePushThrift {

    1: optional string msgId;
    2: optional list<string> userIds;
    3: required string content;
    4: required bool broadcast;
    5: optional list<string> tags;
    6: optional string sendCondition;
    7: optional i64 expireTime;

}

struct MessageReceiveThrift {

    1: required string msgId;
    2: required string userId;

}