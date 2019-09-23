var gnlist = "";
var loadIndex;

function getAccreditList_init(currPage,pageSize) {
    var url = getactionid_manage().getAccreditList;
    var keyword=$("input[name='keyword']").val();
    var data={
        clientName: keyword,
        currPage:currPage,
        pageSize:pageSize
    };

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:10000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url,data,callGetAccreditList);
}

function getProblemTypeList(keyword, currPage, pageSize) {
    var url = getactionid_manage().getAccreditList;
    var data={
        clientName: keyword,
        currPage:currPage,
        pageSize:pageSize
    };
    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:10000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url, data, callGetAccreditList);
}

//获取授权信息
function getPrivilege() {
    var url = getactionid_manage().getPrivilege;
    ajaxSubmitByJson(url, null, callGetPrivilege);
}

//添加授权
function addAccredit() {
    var url = getactionid_manage().addAccredit;

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
        layer.msg("尚未选择授权功能，请点击【权限管理】选取后提交", {icon: 5});
        return false;
    }

    // $("#burnbool").prop("checked", flushbonading.burnbool == 1);
    // $("#burntime").find("option[value='" + flushbonading.burntime + "']").attr("selected", true);

    var foreverBool = $("#foreverBool").prop("checked");//== true ? 1 : 0
    var serverType = $("#serverType").val();

    if(!isNotEmpty(sqDay)){
        sqDay = "0";
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
function deleteAccreditBySsid(ssid) {
    if (!isNotEmpty(ssid)){
        layer.msg("系统异常",{icon: 5});
        return;
    }

    layer.confirm('确定要作废该条授权记录吗', {
        btn: ['确认','取消'], //按钮
        shade: [0.1,'#fff'], //不显示遮罩
    }, function(index){
        var url = getactionid_manage().delAccredit;
        var data={
            ssid:ssid
        };
        ajaxSubmitByJson(url,data,callAddOrDelete);

        layer.close(index);
    }, function(index){
        layer.close(index);
    });
}

function callAddOrDelete(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            layer.msg("操作成功",{icon: 6});
            setTimeout("window.location.reload()",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetAccreditList(data){
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

function opneModal_1() {


    var html = '<form class="layui-form site-inline" style="margin-top: 20px;padding-right: 35px;">\n' +
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
        '                    <label class="layui-form-label"><span style="color: red;">*</span>授权服务类型</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <select name="serverType" id="serverType" lay-verify="required">\n' +
        '                            <option value="police">police</option>\n' +
        '                        </select>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>是否永久授权</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="checkbox" name="foreverBool" id="foreverBool" lay-filter="foreverBool" lay-skin="switch">\n' +
        '                    </div>\n' +
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
        '                            <p>点击上传，或将文件拖拽到此处</p>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="layui-tab-item">\n' +
        '                        <div class="layui-form-item">\n' +
        '                            <label class="layui-form-label"><span style="color: red;">*</span>授权号码</label>\n' +
        '                            <div class="layui-input-block">\n' +
        '                                <input type="text" name="cpuCode" autocomplete="off" placeholder="请输入授权号码" class="layui-input" >\n' +
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
                    addAccredit();
                });

            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });

    });


}