

function getactionid_manage() {
    return {


        main_logout:"/logout",
        admin_index:"/admin",

        loginCaChe:"/loginCaChe",

        //授权功能类型
        getBaseGnTypeList:"/base/getBaseGnTypeList",
        getBaseTypeGnByssid:"/base/getBaseGnTypeByssid",
        addBaseGnType:"/base/addBaseGnType",
        updateBaseGnType:"/base/updateBaseGnType",
        deleteBaseGnTypeByssid:"/base/deleteBaseGnTypeByssid",

        //授权类型
        getBaseTypeList:"/base/getBaseTypeList",
        getBaseTypeByssid:"/base/getBaseTypeByssid",
        addBaseType:"/base/addBaseType",
        updateBaseType:"/base/updateBaseType",
        deleteBaseTypeByssid:"/base/deleteBaseTypeByssid",

        //授权功能管理
        getBaseGnInfoList:"/base/getBaseGnInfoList",
        getBaseGnInfoByssid:"/base/getBaseGnInfoByssid",
        addBaseGnInfo:"/base/addBaseGnInfo",
        updateBaseGnInfo:"/base/updateBaseGnInfo",
        deleteBaseGnInfoByssid:"/base/deleteBaseGnInfoByssid",

        //授权增删改查
        addAuthorize:"/ac/addAuthorize",
        uploadBytxt:"/ac/uploadBytxt",
        delAuthorize:"/ac/delAuthorize",
        getFindByssid:"/ac/getFindByssid",
        getPrivilege:"/ac/getPrivilege",
        downloadSQFile:"/ac/downloadSQFile",
        downloadAllSQFile:"/ac/downloadAllSQFile",

        getAuthorizeList:"/ac/getAuthorizeList",


    };
}