var gnlist = "";
var loadIndex;
var sqgnlist = "";
var sqinfolist = [];
var jiarunum = 0;
var sqinfodata = "";

function getAuthorizeList_init(currPage,pageSize) {
    var url = getauthorize_manage().getAuthorizeList;
    var sq_username=$("input[name='sq_username']").val();
    var keyword=$("input[name='keyword']").val();
    var data={
        username: sq_username,
        clientName: keyword,
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

function getProblemTypeList(keyword, currPage, pageSize) {
    var url = getauthorize_manage().getAuthorizeList;
    var data={
        clientName: keyword,
        currPage:currPage,
        pageSize:pageSize
    };
    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:30000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url, data, callGetAuthorizeList);
}

//获取授权信息
function getPrivilege() {
    var url = getauthorize_manage().getPrivilege;
    ajaxSubmitByJson(url, null, callGetPrivilege);
}

//添加授权
function addAuthorize() {
    var url = getauthorize_manage().addAuthorize;

    var clientName = $("input[name='clientName']").val();
    var unitCode = $("input[name='unitCode']").val();
    var sqDay = $("input[name='sqDay']").val();
    var serverType = $("input[name='serverType']").val();

    var cpuCode = $("input[name='cpuCode']").val();
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

    if (isNotEmpty(clientName) && clientName.length > 9) {
        layer.msg("单位名称长度不能超过9个字符", {icon: 5});
        return false;
    }

    var data = {
        clientName: clientName,
        unitCode: unitCode,
        sqDay: parseInt(sqDay),
        foreverBool: foreverBool,
        serverType: serverType,
        gnlist: gnlist,
        cpuCode: cpuCode
    };

    loadIndex = layer.msg("提交中，请稍后...", {
        icon: 16,
        time:60000,
        shade: [0.1,"#fff"]
    });


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
        btn: ['确认','取消'], //按钮
        shade: [0.1,'#fff'], //不显示遮罩
    }, function(index){
        var url = getauthorize_manage().delAuthorize;
        var data={
            ssid:ssid
        };

        loadIndex = layer.msg("加载中，请稍后...", {
            icon: 16,
            time:30000,
            shade: [0.1,"#fff"]
        });

        ajaxSubmitByJson(url,data,callAddOrDelete);

        layer.close(index);
    }, function(index){
        layer.close(index);
    });
}

//下载授权文件
function downloadFileByPath(clientName, startTime) {

    startTime = startTime.replace(/-|:| /g, "");

    var url = getauthorize_manage().downloadSQFile;
    var path = clientName + startTime;

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time: 30000,
        shade: [0.1, "#fff"]
    });

    window.location.href = url + "/" + path;

    layer.close(loadIndex);
}


//申请页提交表格
function formSubmit() {
    var username=$("input[name='username']").val();
    var companyname=$("input[name='companyname']").val();
    var clientName=$("input[name='clientName']").val();
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

            sqinfoentity += entity.name + "&nbsp;&nbsp;&nbsp;" + entity.value + "<br/>";

        }
    }

    var data={
        username: username,
        companyname:companyname,
        clientName:clientName,
        unitCode:unitCode,
        sqNum:sqNum,
        sqDay:sqDay,
        sqgnlist:sqgnlist,
        companymsg:companymsg,
        sqinfolist:sqinfoentity
    };

    var dataName={
        username: "申请人名称：",
        companyname:"授权公司名称：",
        clientName:"单位名称：",
        unitCode:"单位代码：",
        sqNum:"授权台数：",
        sqDay:"授权总天数：",
        sqgnlist:"客户端的功能列表：",
        companymsg:"公司简介：",
        sqinfolist:"授权码编号："
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



    $("#jiaru").append(jiaruHTML);
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

            var privilege = data.data.shouquan;
            var serverType = data.data.serverType;
            var quanxianHTML = "";

            //获取所有key
            var keys = [];
            for (var property in privilege){
                keys.push(property);
            }

            for (var i = 0; i < keys.length; i++) {

                var item = privilege[keys[i]];

                var itemHTML = '<div class="layui-form-item">' +
                '   <label class="layui-form-label"><span style="color: red;">*</span>' + keys[i] + '</label>' +
                '   <div class="layui-input-block">';

                var inpitHTML = "";
                var indexKey = 0;
                for (var pro in item){
                    var value = item[pro];

                    if (keys[i].indexOf("OEM版本") != -1) {
                        if (indexKey == 0) {
                            inpitHTML += '       <input type="radio" name="oem" value="' + pro + '" title="' + value + '" checked>';
                        } else {
                            inpitHTML += '       <input type="radio" name="oem" value="' + pro + '" title="' + value + '">';
                        }
                    }else if (keys[i].indexOf("分支") != -1) {
                        if (indexKey == 0) {
                            inpitHTML += '       <input type="radio" name="fen" value="' + pro + '" title="' + value + '" checked>';
                        }else{
                            inpitHTML += '       <input type="radio" name="fen" value="' + pro + '" title="' + value + '">';
                        }
                    }else if (keys[i].indexOf("单机版") != -1) {
                        if (indexKey == 0) {
                            inpitHTML += '       <input type="radio" name="version" value="' + pro + '" title="' + value + '" checked>';
                        }else{
                            inpitHTML += '       <input type="radio" name="version" value="' + pro + '" title="' + value + '">';
                        }
                    }else if (keys[i].indexOf("客户端版") != -1) {
                        if (indexKey == 0) {
                            inpitHTML += '       <input type="radio" name="duan" value="' + pro + '" title="' + value + '" checked>';
                        }else{
                            inpitHTML += '       <input type="radio" name="duan" value="' + pro + '" title="' + value + '">';
                        }
                    }else {
                        if (indexKey == 0) {
                            inpitHTML += '       <input type="checkbox" lay-filter="checkbox" name="' + pro + '" title="' + value + '" checked>';
                        }else{
                            inpitHTML += '       <input type="checkbox" lay-filter="checkbox" name="' + pro + '" title="' + value + '">';
                        }
                    }

                    indexKey++;
                }

                quanxianHTML += itemHTML + inpitHTML + '   </div>' +
                    '</div>';
            }

            //输出类型serverType
            var serverTypeHTML = "";
            for (var item in serverType){
                var value = serverType[item];
                serverTypeHTML += '<option value="' + item + '">' + value + '</option>';
            }

            $("#quanxian").html(quanxianHTML);
            $("#serverType").html(serverTypeHTML);

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
function getProblemTypeListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getProblemTypeList_init(currPage, pageSize);
    }  else if (len == 2) {
        getProblemTypeList('', arguments[0], arguments[1]);
    } else if (len > 2) {
        getProblemTypeList(arguments[0], arguments[1], arguments[2]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var keyword=$("input[name='keyword']").val();
        var arrparam=new Array();
        arrparam[0]=keyword;
        showpage("paging",arrparam,'getProblemTypeListParam',currPage,pageCount,pageSize);
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
        '                <label class="layui-form-label"><span style="color: red;">*</span>授权总天数</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="number" name="sqDay" lay-verify="required|number" required  autocomplete="off" placeholder="请输入授权总天数" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>申请台数</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="number" name="sqsize" lay-verify="required|number" required  autocomplete="off" placeholder="请输入申请台数" class="layui-input" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>是否永久授权</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="checkbox" name="foreverBool" id="foreverBool" lay-filter="foreverBool" lay-skin="switch">\n' +
        '                    </div>\n' +
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
        '                            <p>点击上传，或将文件拖拽到此处(可上传zip，rar格式)</p>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="layui-tab-item">\n' +
        '                        <div class="layui-form-item">\n' +
        '                            <label class="layui-form-label"><span style="color: red;">*</span>授权号码</label>\n' +
        '                            <div class="layui-input-block">\n' +
        '                                <textarea name="cpuCode2" id="cpuCode2" placeholder="请输入授权号码，换行可以设置多个授权码&#13;&#10;例子：&#13;&#10;aaaaaaaaaaaaaaaaaaaaaaaaa&#13;&#10;bbbbbbbbbbbbbbbbbbbbbbbbb&#13;&#10;cccccccccccccccccccccccccccc" class="layui-textarea"></textarea>\n' +
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
                var url = getauthorize_manage().uploadBytxt;
                upload.render({
                    elem: '#test10'
                    ,url: url
                    ,acceptMime: '.txt' //只允许上传txt文件
                    ,exts: 'txt' //只允许上传压缩文件
                    , before: function (obj) {
                        $("#updateCpuCode").val("");
                    }
                    ,done: function(res){
                        //上传成功，把授权码放到指定的name里面

                        if (res.actioncode == "SUCCESS") {
                            $("#updateCpuCode").val(res.data);
                            layer.msg("授权文件加载成功，请填写完表单点击确定",{icon: 6});
                        }
                    }
                });
                getPrivilege();//获取功能权限信息
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
        '                    <td>销售李四</td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">授权公司名称：</td>\n' +
        '                    <td>阿里巴巴</td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">单位名称：</td>\n' +
        '                    <td>淘宝</td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">单位代码：</td>\n' +
        '                    <td>taobao</td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">授权台数：</td>\n' +
        '                    <td>10台</td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">授权总天数：</td>\n' +
        '                    <td>100天</td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">客户端的功能列表：</td>\n' +
        '                    <td>笔录管理，语音识别，语音播报，设备控制</td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td align="right">公司简介：</td>\n' +
        '                    <td>是中国最大的IT公司</td>\n' +
        '                </tr>\n' +
        '            </tbody>\n' +
        '        </table>\n' +
        '<div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">\n' +
        '  <ul class="layui-tab-title">\n' +
        '    <li class="layui-this">授权文件列表</li>\n' +
        '  </ul>\n' +
        '  <div class="layui-tab-content">' +
            '        <div style="margin-left: 30px;overflow: hidden;">\n' +
        '            <table border="0" id="tableId">\n' +
        '                <tr>\n' +
        '                    <td>1号</td>\n' +
        '                    <td>aaaaaaaaaaaaaaaaaaaaaaaaa</td>\n' +
        '                    <td><a href="#">下载授权文件</a></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td>2333号</td>\n' +
        '                    <td>bbbbbbbbbbbbbbbbbbbbbbbbb</td>\n' +
        '                    <td><a href="#">下载授权文件</a></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td>3号</td>\n' +
        '                    <td>cccccccccccccccccccccccccccc</td>\n' +
        '                    <td><a href="#">下载授权文件</a></td>\n' +
        '                </tr>\n' +
        '                <tr>\n' +
        '                    <td colspan="3"><button class="layui-btn layui-btn-normal" style="float: right;margin-top: 5px;">打包下载</button></td>\n' +
        '                </tr>\n' +
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