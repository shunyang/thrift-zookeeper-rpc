namespace java com.yangyang.thrift.api.struct


struct User{
    1:required i32 id
    2:required string username
    3:required string password
    4:optional string nickname
    5:optional string createTime

}