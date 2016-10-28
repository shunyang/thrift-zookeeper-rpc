namespace java com.yangyang.thrift.api

include "SmartTypes.thrift"

service SmartService{

SmartTypes.User getUserById(i32 uid)

}
