namespace java com.albedo.java.rpc.pay.service.thrift

service PayThriftService {

    void create(1:string payCreate);

    string query(1:string payQuery);

}


