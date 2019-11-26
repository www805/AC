var loadIndex;

function getBaseTypeList_init(currPage,pageSize) {
    var url = getactionid_manage().getBaseTypeList;
    var sq_typename=$("input[name='sq_typename']").val();
    var data={
        typename: sq_typename,
        currPage:currPage,
        pageSize:pageSize
    };

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:30000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url,data,callGetBaseTypeList);
}

function getBaseTypeList(typename, currPage, pageSize) {
    var url = getactionid_manage().getBaseTypeList;
    var data={
        typename: typename,
        currPage:currPage,
        pageSize:pageSize
    };
    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:30000,
        shade: [0.1,"#fff"]
    });
    ajaxSubmitByJson(url, data, callGetBaseTypeList);
}

//通过ssid获取授权类型
function getBaseTypeByssid(ssid) {
    var url = getactionid_manage().getBaseTypeByssid;

    var data={
        ssid: ssid
    };

    ajaxSubmitByJson(url,data,callGetBaseTypeByssid);
}

//新增授权类型
function addBaseType() {
    var url = getactionid_manage().addBaseType;
    var typename=$("input[name='typename']").val();
    var typecode=$("input[name='typecode']").val();
    var type=$("input[name='type']").prop("checked")==true?1:0;
    var ordernum=$("input[name='ordernum']").val();

    var data={
        typename: typename,
        typecode: typecode,
        type: parseInt(type),
        ordernum: ordernum
    };

    ajaxSubmitByJson(url,data,callGetBaseType);
}

//修改授权类型
function updateBaseType(ssid) {
    var url = getactionid_manage().updateBaseType;
    var typename=$("input[name='typename']").val();
    var typecode=$("input[name='typecode']").val();
    var type=$("#sqtype").val();
    var ordernum=$("input[name='ordernum']").val();

    var data={
        typename: typename,
        typecode: typecode,
        type: parseInt(type),
        ordernum: ordernum,
        ssid: ssid
    };

    ajaxSubmitByJson(url,data,callGetBaseType);
}

//删除一条授权类型
function deleteBaseTypeByssid(ssid) {
    if (!isNotEmpty(ssid)){
        layer.msg("ssid不为空，系统异常",{icon: 5});
        return;
    }

    layer.confirm('确定要删除这个授权类型吗', {
        btn: ['确认','取消'], //按钮
        shade: [0.1,'#fff'], //不显示遮罩
    }, function(index){
        var url = getactionid_manage().deleteBaseTypeByssid;
        var data={
            ssid:ssid
        };

        loadIndex = layer.msg("加载中，请稍后...", {
            icon: 16,
            time:30000,
            shade: [0.1,"#fff"]
        });

        ajaxSubmitByJson(url,data,callGetBaseType);

        layer.close(index);
    }, function(index){
        layer.close(index);
    });
}


function callGetBaseTypeList(data){
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

function callGetBaseTypeByssid(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            var basetype = data.data;
            $("input[name='typename']").val(basetype.typename);
            $("input[name='typecode']").val(basetype.typecode);
            $("#sqtype").find("option[value='" + basetype.type + "']").attr("selected", true);
            $("input[name='ordernum']").val(basetype.ordernum);

            layui.use('form', function () {
                var form = layui.form;
                form.render();
            });
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetBaseType(data){
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
function getBaseTypeListParam() {

    var len = arguments.length;

    if (len == 0) {
        var currPage = 1;
        var pageSize = 10;//测试
        getBaseTypeList_init(currPage, pageSize);
    }  else if (len == 2) {
        getBaseTypeList('', arguments[0], arguments[1]);
    } else if (len > 2) {
        getBaseTypeList(arguments[0], arguments[1], arguments[2]);
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
        showpage("paging",arrparam,'getBaseTypeListParam',currPage,pageCount,pageSize);
    }
}

/**
 * 增改弹框
 */
function opneModal_1(ssid) {


    var modelName = "添加";


    var html = '<form class="layui-form site-inline" style="margin-top: 20px;padding-right: 35px;">\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>类型名称</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="typename" required  lay-verify="required" autocomplete="off" placeholder="请输入类型名称" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>类型代码</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="typecode" required  lay-verify="required" autocomplete="off" placeholder="请输入类型代码" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>是否为单选框</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <select name="sqtype" id="sqtype" lay-verify="required">\n' +
        '                       <option value="0">复选框</option>\n' +
        '                       <option value="1">单选框</option>\n' +
        '                    </select>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label">排序</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="number" name="ordernum" lay-verify="" required  autocomplete="off" placeholder="请输入排序" class="layui-input" value="0" onKeypress="return (/[\\d]/.test(String.fromCharCode(event.keyCode)))">\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>';
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
            area: ['630px', '380px'],
            btn: [modelName, '取消'],
            success: function (layero, index) {
                layero.addClass('layui-form');//添加form标识
                layero.find('.layui-layer-btn0').attr('lay-filter', 'fromContent').attr('lay-submit', '').css("border-color","#4A77D4").css("background-color","#4A77D4");//将按钮弄成能提交的

                if(isNotEmpty(ssid)){
                    getBaseTypeByssid(ssid);
                }

                form.render();
            },
            yes: function (index, layero) {
                //自定义验证规则
                form.verify({
                    typename: [/\S/, '请输入授权类型名称'], typecode: [/\S/, '请输入类型代码']
                });
                //监听提交
                form.on('submit(fromContent)', function (data) {

                    //提交
                    if(isNotEmpty(ssid)){
                        updateBaseType(ssid);
                    }else{
                        addBaseType();
                    }
                });

            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });

    });


}