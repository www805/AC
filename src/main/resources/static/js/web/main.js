var gnlist = "";
var loadIndex;
var sqgnlist = "";
var sqinfolist = [];
var jiarunum = 0;
var sqinfodata = "";
var sqEntityInfo = "";

function getAuthorizeList_init(currPage,pageSize) {
    var url = getactionid_manage().getAuthorizeList;
    var sq_username=$("input[name='sq_username']").val();
    var keyword=$("input[name='keyword']").val();
    var sqtypessid=$("#sqtypessid").val();
    var data={
        username: sq_username,
        clientName: keyword,
        batypessid: sqtypessid,
        currPage:currPage,
        pageSize:pageSize
    };

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:30000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url,data,callGetAuthorizeList);
}

function getProblemTypeList(sq_username, keyword, sqtypessid, currPage, pageSize) {
    var url = getactionid_manage().getAuthorizeList;
    var data = {
        username: sq_username,
        clientName: keyword,
        batypessid: sqtypessid,
        currPage: currPage,
        pageSize: pageSize
    };
    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time: 30000,
        shade: [0.1, "#fff"]
    });
    ajaxSubmitByJson(url, data, callGetAuthorizeList);
}

//获取全部授权类型
function getBaseType() {
    var url = getactionid_manage().getBaseTypeList;
    var data={

    };
    ajaxSubmitByJson(url, data, callGetBaseType);
}

//获取授权信息
function getPrivilege() {
    var url = getactionid_manage().getPrivilege;
    ajaxSubmitByJson(url, null, callGetPrivilege);
}

//添加授权
function addAuthorize() {
    var url = getactionid_manage().addAuthorize;

    var username = $("input[name='username']").val();
    var clientName = $("input[name='clientName']").val();
    var unitCode = $("input[name='unitCode']").val();
    var sqDay = $("input[name='sqDay']").val();
    var sqsize = $("input[name='sqsize']").val();
    var batypessid = $("#batypessid").val();

    var cpuCode = $("#cpuCode2").val();
    var updateCpuCode = $("#updateCpuCode").val();

    if (!isNotEmpty(updateCpuCode) && !isNotEmpty(cpuCode)) {
        layer.msg("请上传授权文件或填写授权码", {icon: 5});
        return false;
    }
    if (!isNotEmpty(cpuCode)) {
        cpuCode = updateCpuCode;
    }

    if (!isNotEmpty(gnlist)) {
        layer.msg("尚未选择授权功能，选取后提交", {icon: 5});
        return false;
    }



    var ng_list_str = "";

    $('#quanxian .layui-form-checked').each(function(){//获取所有功能列表的值
        var ng_str = $(this).html()
        var ng_title = ng_str.substring(ng_str.indexOf("<span>") + 6, ng_str.indexOf("</span>"));
        ng_list_str += ng_title + ",";
    });

    if(!isNotEmpty(ng_list_str)){
        layer.msg("请至少选择一个客户端的功能",{icon: 5});
        return;
    }

    var companyname = $("input[name='companyname']").val();
    var companymsg = $("textarea[name='companymsg']").val();
    if(isNotEmpty(companyname) && isNotEmpty(companymsg)){
        var company = "{companyname:" + companyname + ",companymsg:" + companymsg + "}";
        if(isNotEmpty(gnlist)){
            gnlist += "|" +company ;
        }else{
            gnlist += company;
        }
    }

    // $("#burnbool").prop("checked", flushbonading.burnbool == 1);
    // $("#burntime").find("option[value='" + flushbonading.burntime + "']").attr("selected", true);

    var foreverBool = $("#foreverBool").prop("checked");//== true ? 1 : 0
    var serverType = $("#serverType").val();

    if(!isNotEmpty(sqDay)){
        sqDay = "0";
    }

    if (isNotEmpty(clientName) && clientName.length > 20) {
        layer.msg("单位名称长度不能超过20个字符", {icon: 5});
        return false;
    }

    var data = {
        username: username,
        companyname: companyname,
        clientName: clientName,
        unitCode: unitCode,
        sqsize: sqsize,
        sqDay: parseInt(sqDay),
        foreverBool: foreverBool,
        serverType: serverType,
        batypessid: batypessid,
        gnlist: gnlist,
        cpuCode: cpuCode,
        companymsg: companymsg
    };

    loadIndex = layer.msg("提交中，请稍后...", {
        icon: 16,
        time:60000,
        shade: [0.1,"#fff"]
    });


    // console.log(data);

    $.ajax({
        url : url,
        type : "POST",
        async : true,
        dataType : "json",
        contentType: "application/json",
        data : JSON.stringify(data),
        timeout : 60000,
        success :function (data) {
            callAddOrDelete(data)
        },
        error : function (data) {
            console.log(data);
        }
    });


    // ajaxSubmitByJson(url, data, callAddOrDelete());
}

//删除一条授权记录
function deleteAuthorizeBySsid(ssid) {
    if (!isNotEmpty(ssid)){
        layer.msg("ssid不为空，系统异常",{icon: 5});
        return;
    }

    layer.confirm('确定要删除该条授权记录吗', {
        btn: ['确认', '取消'], //按钮
        shade: [0.1, '#fff'], //不显示遮罩
    }, function (index) {
        var url = getactionid_manage().delAuthorize;
        var data = {
            ssid: ssid
        };

        loadIndex = layer.msg("加载中，请稍后...", {
            icon: 16,
            time: 30000,
            shade: [0.1, "#fff"]
        });

        ajaxSubmitByJson(url, data, callAddOrDelete);

        layer.close(index);
    }, function (index) {
        layer.close(index);
    });
}

//下载授权文件
function downloadFileByPath(clientName, startTime) {

    startTime = startTime.replace(/-|:| /g, "");

    var url = getactionid_manage().downloadSQFile;
    var path = clientName + startTime;

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time: 30000,
        shade: [0.1, "#fff"]
    });

    window.location.href = url + "/" + path;

    layer.close(loadIndex);
}

//批量打包下载授权文件
function downloadAllSQFile(ssid) {
    var url = getactionid_manage().downloadAllSQFile;

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time: 30000,
        shade: [0.1, "#fff"]
    });

    window.location.href = url + "/" + ssid;

    layer.close(loadIndex);
}

//获取指定ssid的授权信息
function getFindByssid(ssid) {
    var url = getactionid_manage().getFindByssid;

    var data = {
        ssid: ssid
    };

    ajaxSubmitByJson(url, data, callGetFindByssid);
}

//申请页提交表格
function formSubmit() {
    var username=$("input[name='username']").val();
    var companyname=$("input[name='companyname']").val();
    var clientName=$("input[name='clientName']").val();
    var batypessid=$("#batypessid").find("option:selected").text();
    var unitCode=$("input[name='unitCode']").val();
    var sqNum=$("input[name='sqNum']").val();
    var sqDay=$("input[name='sqDay']").val();
    var companymsg=$("textarea[name='companymsg']").val();

    if($("#foreverBool").prop('checked')){
        sqDay = "永久";//选中就是永久
    }

    var sqinfoentity = "";

    if (isNotEmpty(sqinfolist) && sqinfolist.length > 0) {
        for (var i = 0; i < sqinfolist.length; i++) {
            var entity = sqinfolist[i];

            sqinfoentity += entity.name + "|" + entity.value + "<br/>";//&nbsp;&nbsp;&nbsp;

        }
    }
    //textarea转换换行符
    companymsg = companymsg.replace(/\n/g,"<br/>");

    var ng_list_str = "";
    var qt_list_str = "";

    $('#sqgns .layui-form-checked').each(function(){//获取所有功能列表的值
        var ng_str = $(this).html()
        var ng_title = ng_str.substring(ng_str.indexOf("<span>") + 6, ng_str.indexOf("</span>"));
        ng_list_str += ng_title + ",";
    });

    $('#sqgns .layui-form-radioed').each(function(){//获取所有单选框的值
        var qt_str = $(this).html()
        var qt_title = qt_str.substring(qt_str.indexOf("<div>") + 5, qt_str.indexOf("</div>"));
        qt_list_str += qt_title + ",";
    });

    if(!isNotEmpty(ng_list_str)){
        layer.msg("请至少选择一个客户端的功能",{icon: 5});
        return;
    }

    sqgnlist = ng_list_str + qt_list_str;

    var data={
        username: username,
        companyname:companyname,
        clientName:clientName,
        unitCode:unitCode,
        batypessid:batypessid,
        sqNum:sqNum,
        sqDay:sqDay,
        sqgnlist:sqgnlist,
        sqinfolist:sqinfoentity,
        companymsg:companymsg
    };

    var dataName={
        username: "申请人名称：",
        companyname:"授权公司名称：",
        clientName:"单位名称：",
        unitCode:"单位代码：",
        batypessid:"授权类型：",
        sqNum:"授权台数：",
        sqDay:"授权总天数：",
        sqgnlist:"客户端的功能列表：",
        sqinfolist:"授权码编号：",
        companymsg:"公司简介："
    };

    var formHTML = "";
    sqinfodata = "";

    for (var key in data) {
        formHTML += '<tr>\n' +
            '        <td class="td_right">' + dataName[key] + '</td>\n' +
            '        <td>' + data[key] + '</td>\n' +
            '    </tr>';    

        sqinfodata += dataName[key] + data[key] + "\n";
    }

    sqinfodata = sqinfodata.replace(/&nbsp;/g," ");
    sqinfodata = sqinfodata.replace(/<br\/>/g,"\n");

    formHTML += '<tr>\n' +
        '        <td colspan="2" style="padding-top: 20px;">\n' +
        '            <button style="float: right;" class="layui-btn layui-btn-normal" onclick="copysqinfo();">点我复制</button>\n' +
        '            <button style="float: right;margin-right: 50px;" onclick=\'$("#sqinif").hide();$("#da_row").show();\' class="layui-btn layui-btn-normal" >返回</button>\n' +
        '        </td>\n' +
        '    </tr>';


    $("#sqinif").html(formHTML).show();
    $("#da_row").hide();
    sqinfolist = [];
    // console.log(sqinfodata);
}

//新增一行授权码
function addsqinfo() {

    jiarunum++;

    var jiaruHTML = "";

    if (jiarunum == 1) {
        jiaruHTML = '<div class="layui-input-inline">\n' +
            '                            <input type="text" name="sqnum' + jiarunum + '" required="" lay-verify="" autocomplete="off" placeholder="请输入编号如：' + jiarunum + '号" class="layui-input">\n' +
            '                        </div>\n' +
            '                        <label class="layui-form-label sqbianhao" >授权码</label>\n' +
            '                        <div class="layui-input-inline " style="width: 350px;margin-bottom: 5px;">\n' +
            '                            <input type="text" name="sqCode' + jiarunum + '" required="" lay-verify="" autocomplete="off" placeholder="请输入授权码，用户的机器授权码" class="layui-input">\n' +
            '                        </div>';
    }else{
        jiaruHTML = '<div id="sqkuang' + jiarunum + '"><label class="layui-form-label" style="clear:both;"></label>\n' +
            '                        <div class="layui-input-inline">\n' +
            '                            <input type="text" name="sqnum' + jiarunum + '" required="" lay-verify="" autocomplete="off" placeholder="请输入编号如：' + jiarunum + '号" class="layui-input">\n' +
            '                        </div>\n' +
            '                        <label class="layui-form-label sqbianhao" >授权码</label>\n' +
            '                        <div class="layui-input-inline" style="width: 350px;margin-bottom: 5px;">\n' +
            '                            <input type="text" name="sqCode' + jiarunum + '" required="" lay-verify="" autocomplete="off" placeholder="请输入授权码，用户的机器授权码" class="layui-input">\n' +
            '                        </div>\n' +
            '                        <div class="layui-input-inline" >\n' +
            '                            <input type="button" class="layui-btn layui-btn-danger" value="删除" onclick="$(\'#sqkuang' + jiarunum + '\').remove();">\n' +
            '                        </div>' +
            '</div>';
    }



    $("#jiaru,#sqcodelists").append(jiaruHTML);
}

function callGetFindByssid(data){
    layer.close(loadIndex);
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            sqEntityInfo = data.data;

            var url = getactionid_manage().downloadSQFile;

            $("#sqinifo_username").html(sqEntityInfo.username);
            $("#sqinifo_companyname").html(sqEntityInfo.companyname);
            $("#sqinifo_clientName").html(sqEntityInfo.clientName);
            $("#sqinifo_unitCode").html(sqEntityInfo.unitCode);
            $("#sqinifo_sqsize").html(sqEntityInfo.sqsize);
            $("#sqinifo_sqDay").html(sqEntityInfo.sqDay == 0 ? '永久' : sqEntityInfo.sqDay);
            $("#sqinifo_gnlist").html(sqEntityInfo.gnlist);
            $("#sqinifo_companymsg").html(sqEntityInfo.companymsg);

            var sqCodeList = sqEntityInfo.sqCodeList;
            var sqCodeListHTML = "";

            if (isNotEmpty(sqCodeList)) {
                for (var i = 0; i < sqCodeList.length; i++) {
                    var sqCode = sqCodeList[i];

                    //输出授权码
                    sqCodeListHTML += '<tr>\n' +
                        '        <td>' + sqCode.name + '</td>\n' +
                        '        <td title="' + sqCode.sqcode + '">' + sqCode.sqcode.substring(0, 53) + '</td>\n' +
                        '        <td><a href="' + url + '/' + sqCode.ssid + '">下载授权文件</a></td>\n' +
                        '</tr>';
                }

                sqCodeListHTML += '<tr>\n' +
                    '                    <td colspan="3"><button class="layui-btn layui-btn-normal" onclick="downloadAllSQFile(\'' + sqEntityInfo.ssid + '\');" style="float: right;margin-top: 5px;">打包下载</button></td>\n' +
                    '                </tr>\n';

            }

            $("#sqinifo_tableId").prepend(sqCodeListHTML);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callAddOrDelete(data){
    layer.close(loadIndex);
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            layer.msg("操作成功",{icon: 6});
            setTimeout("window.location.reload()",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetAuthorizeList(data){
    layer.close(loadIndex);//关闭load特效
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            $("#resultSize").html(data.data.pageparam.recordCount);
            pageshow(data);
            var listcountsize = data.data.pagelist;
            if (listcountsize == null || listcountsize.length == 0) {
                $("#wushuju").show();
                $("#pagelistview").hide();
            } else {
                $("#wushuju").hide();
                $("#pagelistview").show();
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetPrivilege(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){

            // var privilege = data.data;
            var serverType = data.data;
            var quanxianHTML = "";

            for (var i = 0; i < serverType.length; i++) {

                var item = serverType[i];
                var gninfoList = serverType[i].baseGninfo;

                var itemHTML = '<div class="layui-form-item">' +
                '   <label class="layui-form-label"><span style="color: red;">*</span>' + item.typename + '</label>' +
                '   <div class="layui-input-block">';

                var inpitHTML = "";
                var indexKey = 0;
                for (var num in gninfoList){
                    var gninfo = gninfoList[num];

                    if(item.type == 1){
                        if (indexKey == 0) {
                            inpitHTML += '       <input type="radio" name="' + item.typecode + '" value="' + gninfo.name + '" title="' + gninfo.title + '" checked>';
                        } else {
                            inpitHTML += '       <input type="radio" name="' + item.typecode + '" value="' + gninfo.name + '" title="' + gninfo.title + '">';
                        }
                    }else{
                        if (indexKey == 0) {
                            inpitHTML += '       <input type="checkbox" lay-filter="checkbox" name="' + gninfo.name + '" title="' + gninfo.title + '" >';
                        }else{
                            inpitHTML += '       <input type="checkbox" lay-filter="checkbox" name="' + gninfo.name + '" title="' + gninfo.title + '">';
                        }
                    }

                    indexKey++;
                }

                quanxianHTML += itemHTML + inpitHTML + '   </div>' +
                    '</div>';
            }

            $("#sqgns,#quanxian").html(quanxianHTML);
            // $("#serverType").html(serverTypeHTML);

            layui.use('form', function () {
                var form = layui.form;
                form.render();
            })

        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetBaseType(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var sqtypes = data.data.pagelist;
            var sqtypeHtml = "<option value='0'>全部类型</option>";
            var typeHtml = "";

            for (var i = 0; i < sqtypes.length; i++) {
                var sqtype = sqtypes[i];
                typeHtml += '<option value="'+sqtype.ssid+'">'+sqtype.typename+'</option>';
            }
            $("#sqtypessid").html(sqtypeHtml + typeHtml);
            $("#batypessid").html(typeHtml);
            layui.use('form', function () {
                var form = layui.form;
                form.render();
            })
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

/**
 * 局部刷新
 */
function getAuthorizeListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getProblemTypeList_init(currPage, pageSize);
    }  else if (len == 2) {
        getProblemTypeList('', arguments[0], arguments[1]);
    } else if (len > 2) {
        getProblemTypeList(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var sq_username=$("input[name='sq_username']").val();
        var keyword=$("input[name='keyword']").val();
        var sqtypessid=$("#sqtypessid").val();

        var arrparam=new Array();
        arrparam[0]=sq_username;
        arrparam[1]=keyword;
        arrparam[2]=sqtypessid;
        showpage("paging",arrparam,'getAuthorizeListParam',currPage,pageCount,pageSize);
    }
}

/**
 * 复制
 */
function copysqinfo() {

    // var val = document.querySelector("p").innerText; // 要复制的内容
    document.addEventListener('copy', save); // 监听浏览器copy事件
    document.execCommand('copy'); // 执行copy事件，这时监听函数会执行save函数。
    document.removeEventListener('copy', save); // 移除copy事件
    console.log(sqinfodata);
    // 保存方法
    function save(e) {
        e.clipboardData.setData('text/plain', sqinfodata); // 剪贴板内容设置
        e.preventDefault();
        layer.msg("已经复制到剪辑版",{icon: 6});
    }
}

/**
 * 自用弹框
 */
function opneModal_1() {

    var html = '<form class="layui-form site-inline" style="margin-top: 20px;padding-right: 35px;">\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>申请人名称</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="username" required  lay-verify="required" autocomplete="off" placeholder="请输入申请人名称" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>公司名称</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="companyname" required  lay-verify="required" autocomplete="off" placeholder="请输入公司名称" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>单位名称</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="clientName"  required lay-verify="required" autocomplete="off" placeholder="请输入单位名称" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>单位代码</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="unitCode" required  lay-verify="required" autocomplete="off" placeholder="请输入单位代码如：avst" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>申请台数</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="number" name="sqsize" lay-verify="required|number" required  autocomplete="off" placeholder="请输入申请台数" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>授权总天数</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="number" name="sqDay" lay-verify="required|number" required  autocomplete="off" placeholder="请输入授权总天数" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>是否永久授权</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="checkbox" name="foreverBool" id="foreverBool" lay-filter="foreverBool" lay-skin="switch">\n' +
        '                    </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>授权类型</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <select name="batypessid" id="batypessid" lay-verify="required">\n' +
        '                    </select>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>公司简介</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <textarea name="companymsg" placeholder="请输入公司简介" lay-verify="required" class="layui-textarea"></textarea>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div id="guanli" class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief" style="margin-left: 20px;margin-bottom: 60px;">\n' +
        '                <ul class="layui-tab-title">\n' +
        '                    <li class="layui-this"><span style="color: red;">*</span>上传授权文件</li>\n' +
        '                    <li>授权码授权</li>\n' +
        '                </ul>\n' +
        '                <div class="layui-tab-content" style="height: 100px;padding-top: 15px;">\n' +
        '                    <div class="layui-tab-item layui-show" >\n' +
        '                        <input type="hidden" id="updateCpuCode" name="updateCpuCode" value=""/>\n' +
        '                        <div class="layui-upload-drag" id="test10" style="width: 510px;height: 90px;">\n' +
        '                            <i class="layui-icon"></i>\n' +
        '                            <p>点击上传，或将文件拖拽到此处(可上传zip格式)</p>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="layui-tab-item">\n' +
        '                        <div class="layui-form-item" id="sqcodelists">\n' +
        '                            <label class="layui-form-label"><span style="color: red;">*</span>授权号码</label>\n' +
        '                            <div class="layui-input-block">\n' +
        '                                <textarea name="cpuCode2" id="cpuCode2" placeholder="请输入授权号码，换行可以设置多个授权码&#13;&#10;例子：&#13;&#10;11|aaaaaaaaaaaaaaaaaaaaaaaaa&#13;&#10;22|bbbbbbbbbbbbbbbbbbbbbbbbb&#13;&#10;33|cccccccccccccccccccccccccccc" class="layui-textarea"></textarea>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item" id="quanxian">\n' +
        '                  <label class="layui-form-label"><span style="color: red;">*</span>授予权限</label>\n' +
        '                  <div class="layui-input-block">\n' +
        '                       <input type="checkbox" name="record" title="笔录制作" checked>\n' +
        '                       <input type="checkbox" name="asr" title="语音识别">\n' +
        '                       <input type="checkbox" name="fd" title="设备控制">\n' +
        '                       <input type="checkbox" name="ph" title="不知道是啥">\n' +
        '                  </div>\n' +
        '            </div>\n' +
        '        </form>';


    layui.use(['form', 'upload'], function () {
        var form = layui.form;
        var upload = layui.upload;

        var index = layer.open({
            type: 1,
            title: '添加授权',
            content: html,
            area: ['630px', '650px'],
            btn: ['确定', '取消'],
            success: function (layero, index) {
                layero.addClass('layui-form');//添加form标识
                layero.find('.layui-layer-btn0').attr('lay-filter', 'fromContent').attr('lay-submit', '').css("border-color","#4A77D4").css("background-color","#4A77D4");//将按钮弄成能提交的
                //拖拽上传
                var url = getactionid_manage().uploadBytxt;
                upload.render({
                    elem: '#test10'
                    ,url: url
                    ,acceptMime: '.txt,.zip' //只允许上传txt文件
                    ,exts: 'txt|zip' //只允许上传压缩文件
                    , before: function (obj) {
                        $("#updateCpuCode").val("");
                    }
                    ,done: function(res){
                        //上传成功，把授权码放到指定的name里面

                        if (res.actioncode == "SUCCESS") {
                            $("#cpuCode2").val(res.data);//#updateCpuCode
                            layer.msg("授权文件加载成功，请填写完表单点击确定",{icon: 6});
                        }else{
                            layer.msg("授权文件上传失败...",{icon: 5})
                        }
                    }
                });
                getPrivilege();//获取功能权限信息
                getBaseType();//获取授权类型
                form.render();
            },
            yes: function (index, layero) {
                //自定义验证规则
                form.verify({
                    typename: [/\S/, '请输入问题类型名称'], ordernum: [/\S/, '请输入问题排序号']
                });
                //监听提交
                form.on('submit(fromContent)', function (data) {
                    gnlist = "";
                    $("#quanxian input[type='checkbox']:checked").each(function(index, element){
                        if(isNotEmpty(gnlist)){
                            gnlist += "|" + this.name;
                        }else{
                            gnlist += this.name;
                        }
                    });
                    $("#quanxian input[type='radio']:checked").each(function(index, element){

                        if(isNotEmpty(gnlist)){
                            gnlist += "|" + this.value;
                        }else{
                            gnlist += this.value;
                        }
                    });

                    //提交
                    addAuthorize();
                });

            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });

    });


}

/**
 * 授权详情弹框
 */
function opneModal_2(ssid) {

    var html = '<table class="layui-table" lay-even="" lay-skin="nob">\n' +
        '            <colgroup>\n' +
        '                <col width="160">\n' +
        '                <col>\n' +
        '            </colgroup>\n' +
        '            <tbody>\n' +
        '                <tr>\n' +
        '                    <td align="right">申请人名称：</td>\n' +
        '                    <td id="sqinifo_username"></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">授权公司名称：</td>\n' +
        '                    <td id="sqinifo_companyname"></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">单位名称：</td>\n' +
        '                    <td id="sqinifo_clientName"></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">单位代码：</td>\n' +
        '                    <td id="sqinifo_unitCode"></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">授权台数：</td>\n' +
        '                    <td id="sqinifo_sqsize"></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">授权总天数：</td>\n' +
        '                    <td id="sqinifo_sqDay"></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">客户端的功能列表：</td>\n' +
        '                    <td id="sqinifo_gnlist"></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">公司简介：</td>\n' +
        '                    <td id="sqinifo_companymsg"></td>\n' +
        '                </tr>\n' +
        '            </tbody>\n' +
        '        </table>\n' +
        '<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">\n' +
        '  <ul class="layui-tab-title">\n' +
        '    <li class="layui-this">授权文件列表</li>\n' +
        '  </ul>\n' +
        '  <div class="layui-tab-content">' +
            '        <div style="margin-left: 30px;overflow: hidden;">\n' +
        '            <table border="0" width="100%" id="sqinifo_tableId">\n' +

        '            </table>\n' +
        '        </div>' +
        '   </div>\n' +
        '</div>\n';

    layui.use(['form', 'upload'], function () {
        var form = layui.form;
        var upload = layui.upload;

        var index = layer.open({
            type: 1,
            title: '授权详情',
            content: html,
            area: ['630px', '650px'],
            btn: ['返回'],
            success: function (layero, index) {
                loadIndex = layer.msg("加载中，请稍后...", {
                    icon: 16,
                    time:30000,
                    shade: [0.1,"#fff"]
                });
                getFindByssid(ssid);
            },
            yes: function (index, layero) {
                layer.close(index);
            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });

    });


}