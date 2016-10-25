namespace java com.yangyang.thrift.service.api

include "SmartTypes.thrift"

service SmartService{

SmartTypes.User getUserById(i32 uid)

}
