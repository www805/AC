var loadIndex;

function getBaseGnInfoList_init(currPage,pageSize) {
    var url = getactionid_manage().getBaseGnInfoList;
    var keyword=$("input[name='keyword']").val();
    var data={
        title: keyword,
        currPage:currPage,
        pageSize:pageSize
    };

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:30000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url,data,callGetBaseGnInfoList);
}

function getBaseGnInfoList(keyword, currPage, pageSize) {
    var url = getactionid_manage().getBaseGnInfoList;
    var data={
        title: keyword,
        currPage:currPage,
        pageSize:pageSize
    };
    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:30000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url, data, callGetBaseGnInfoList);
}

//通过ssid获取授权类型
function getBaseGnInfoByssid(ssid) {
    var url = getactionid_manage().getBaseGnInfoByssid;

    var data={
        ssid: ssid
    };

    ajaxSubmitByJson(url,data,callGetBaseGnInfoByssid);
}

//新增授权类型
function addBaseGnInfo() {
    var url = getactionid_manage().addBaseGnInfo;
    var title=$("input[name='title']").val();
    var name=$("input[name='name']").val();
    var type=$("input[name='type']").prop("checked")==true?1:0;
    var btypessid = $("#sqtypename").val();

    var data={
        title: title,
        name: name,
        type: parseInt(type),
        btypessid: btypessid
    };

    ajaxSubmitByJson(url,data,callGetBaseGnInfo);
}

//修改授权类型
function updateBaseGnInfo(ssid) {
    var url = getactionid_manage().updateBaseGnInfo;
    var title=$("input[name='title']").val();
    var name=$("input[name='name']").val();
    var btypessid = $("#sqtypename").val();

    var data={
        title: title,
        name: name,
        btypessid: btypessid,
        ssid: ssid
    };

    ajaxSubmitByJson(url,data,callGetBaseGnInfo);
}

//删除一条授权类型
function deleteBaseGnInfoByssid(ssid) {
    if (!isNotEmpty(ssid)){
        layer.msg("ssid不为空，系统异常",{icon: 5});
        return;
    }

    layer.confirm('确定要删除这个授权类型吗', {
        btn: ['确认','取消'], //按钮
        shade: [0.1,'#fff'], //不显示遮罩
    }, function(index){
        var url = getactionid_manage().deleteBaseGnInfoByssid;
        var data={
            ssid:ssid
        };

        loadIndex = layer.msg("加载中，请稍后...", {
            icon: 16,
            time:30000,
            shade: [0.1,"#fff"]
        });

        ajaxSubmitByJson(url,data,callGetBaseGnInfo);

        layer.close(index);
    }, function(index){
        layer.close(index);
    });
}

//获取授权类型
function getBaseType() {
    var url = getactionid_manage().getBaseGnTypeList;
    var data={};
    ajaxSubmitByJson(url,data,callGetBaseType);
}

function callGetBaseType(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){

            var list = data.data.pagelist;
            if (isNotEmpty(list)) {

                var typeHTML = "";

                for (var i = 0; i < list.length; i++) {
                    var sqtype = list[i];
                    typeHTML += '<option value="' + sqtype.ssid + '">' + sqtype.typename + '</option>';
                }

                $("#sqtypename").html(typeHTML);

                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                });
            }
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetBaseGnInfoList(data){
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

function callGetBaseGnInfoByssid(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var basetype = data.data;
            $("input[name='title']").val(basetype.title);
            $("input[name='name']").val(basetype.name);
            $("#sqtypename").find("option[value='" + basetype.bgntypessid + "']").attr("selected", true);

            layui.use('form', function () {
                var form = layui.form;
                form.render();
            });
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetBaseGnInfo(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        layer.msg(data.message,{icon: 6});
        setTimeout("window.location.reload()",1500);
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

/**
 * 局部刷新
 */
function getBaseGnInfoListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getBaseGnInfoList_init(currPage, pageSize);
    }  else if (len == 2) {
        getBaseGnInfoList('', arguments[0], arguments[1]);
    } else if (len > 2) {
        getBaseGnInfoList(arguments[0], arguments[1], arguments[2]);
    }
}

function showpagetohtml(){

    if(isNotEmpty(pageparam)){
        var pageSize=pageparam.pageSize;
        var pageCount=pageparam.pageCount;
        var currPage=pageparam.currPage;

        var typename=$("input[name='sq_typename']").val();
        var arrparam=new Array();
        arrparam[0]=typename;
        showpage("paging",arrparam,'getBaseGnInfoListParam',currPage,pageCount,pageSize);
    }
}

/**
 * 增改弹框
 */
function opneModal_1(ssid) {


    var html = '<form class="layui-form site-inline" style="margin-top: 20px;padding-right: 35px;">\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>功能标题</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="title" required  lay-verify="required" autocomplete="off" placeholder="请输入功能标题" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>授权代号</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="name" required  lay-verify="required" autocomplete="off" placeholder="请输入授权代号" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>授权类型</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <select name="typename" id="sqtypename" lay-verify="required">\n' +
        '                       <option value="0">选择类型</option>\n' +
        '                    </select>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>';


    var modelName = "添加";
    if (isNotEmpty(ssid)) {
        modelName = "修改";
    }

    layui.use(['form', 'upload'], function () {
        var form = layui.form;
        var upload = layui.upload;

        var index = layer.open({
            type: 1,
            title: modelName + '授权类型',
            content: html,
            area: ['630px', '350px'],
            btn: [modelName, '取消'],
            success: function (layero, index) {
                layero.addClass('layui-form');//添加form标识
                layero.find('.layui-layer-btn0').attr('lay-filter', 'fromContent').attr('lay-submit', '').css("border-color","#4A77D4").css("background-color","#4A77D4");//将按钮弄成能提交的

                getBaseType();
                if(isNotEmpty(ssid)){
                    setTimeout(function () {
                        getBaseGnInfoByssid(ssid);
                    },300);
                }

                form.render();
            },
            yes: function (index, layero) {
                //自定义验证规则
                form.verify({
                    typename: [/\S/, '请输入授权功能名称'], typecode: [/\S/, '请输入功能代码']
                });
                //监听提交
                form.on('submit(fromContent)', function (data) {

                    //提交
                    if(isNotEmpty(ssid)){
                        updateBaseGnInfo(ssid);
                    }else{
                        addBaseGnInfo();
                    }
                });

            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });

    });


}