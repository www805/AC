
function getProblemTypeList_init(currPage,pageSize) {
    var url=getActionURL(getactionid_manage().problemIndex_getProblemTypes);
    var keyword=$("input[name='keyword']").val();
    var data={
        token:INIT_CLIENTKEY,
        param:{
            typename: keyword,
            currPage:currPage,
            pageSize:pageSize
        }
    };
    ajaxSubmitByJson(url,data,callProblemTypeList);
}

function getProblemTypeList(keyword, currPage, pageSize) {
    var url=getActionURL(getactionid_manage().problemIndex_getProblemTypes);
    var data={
        token:INIT_CLIENTKEY,
        param:{
            typename: keyword,
            currPage:currPage,
            pageSize:pageSize
        }
    };
    ajaxSubmitByJson(url, data, callProblemTypeList);
}

function getProblemTypeById(ssidd) {
    var url=getActionURL(getactionid_manage().problemIndex_getTemplateTypeById);
    ssid = ssidd;
    var data={
        token:INIT_CLIENTKEY,
        param:{
            ssid: ssid
        }
    };
    ajaxSubmitByJson(url,data,callProblemTypeById);
}

function AddOrUpdateProblemType(version) {
    var url=getActionURL(getactionid_manage().problemIndex_updateProblemType);
    var typename=$("input[name='typename']").val();
    var ordernum=$("input[name='ordernum']").val();
    if (isNotEmpty(version)) {
        //添加
        url=getActionURL(getactionid_manage().problemIndex_addProblemType);
    }
    var data = {
        token: INIT_CLIENTKEY,
        param: {
            ssid: ssid,
            typename: typename,
            ordernum: parseInt(ordernum)
        }
    };
    ajaxSubmitByJson(url, data, callAddOrUpdate);
}

function callAddOrUpdate(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            if (data.data.bool) {
                layer.msg("操作成功",{icon: 6});
            }else{
                layer.msg("操作失败",{icon: 5});
            }
            setTimeout("window.location.reload()",1500);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callProblemTypeList(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data)){
            pageshow(data);
            var listcountsize = data.data.pageparam.recordCount;
            if (listcountsize == 0) {
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

function callProblemTypeById(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            opneModal_1(data.data);
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
        '                <label class="layui-form-label"><span style="color: red;">*</span>公司名称</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="typename" lay-verify="typename" required  autocomplete="off" placeholder="请输入公司名称" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>公司简称</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="text" name="clientName" lay-verify="typename" required  autocomplete="off" placeholder="请输入公司简称如：avst" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>授权总天数</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="number" name="sqDay" lay-verify="ordernum" required  autocomplete="off" placeholder="请输入授权总天数" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <label class="layui-form-label"><span style="color: red;">*</span>授权几台客户端</label>\n' +
        '                <div class="layui-input-block">\n' +
        '                    <input type="number" name="sortNum" lay-verify="ordernum" required  autocomplete="off" placeholder="请输入授权几台客户端" class="layui-input" >\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div class="layui-form-item">\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>是否永久授权</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <input type="checkbox" name="foreverBool" lay-skin="switch">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="layui-inline">\n' +
        '                    <label class="layui-form-label"><span style="color: red;">*</span>授权服务类型</label>\n' +
        '                    <div class="layui-input-block">\n' +
        '                        <select name="serverType" lay-verify="required">\n' +
        '                            <option value="police">police</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            <div id="guanli" class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief" style="margin-left: 20px;">\n' +
        '                <ul class="layui-tab-title">\n' +
        '                    <li class="layui-this">上传授权文件</li>\n' +
        '                    <li>授权码授权</li>\n' +
        '                    <li><span style="color: red;">*</span>权限管理</li>\n' +
        '                </ul>\n' +
        '                <div class="layui-tab-content" style="height: 100px;padding-top: 15px;">\n' +
        '                    <div class="layui-tab-item layui-show" style="margin-left: 60px;">\n' +
        '                        <input type="hidden" id="updateCpuCode" name="updateCpuCode" value=""/>\n' +
        '                        <div class="layui-upload-drag" id="test10" style="width: 400px;">\n' +
        '                            <i class="layui-icon"></i>\n' +
        '                            <p>点击上传，或将文件拖拽到此处</p>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="layui-tab-item">\n' +
        '                        <div class="layui-form-item">\n' +
        '                            <label class="layui-form-label"><span style="color: red;">*</span>授权号码</label>\n' +
        '                            <div class="layui-input-block">\n' +
        '                                <input type="text" name="cpuCode" lay-verify="typename" required  autocomplete="off" placeholder="请输入授权号码" class="layui-input" >\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="layui-tab-item">\n' +
        '                        <div class="layui-form-item">\n' +
        '                            <label class="layui-form-label"><span style="color: red;">*</span>授予权限</label>\n' +
        '                            <div class="layui-input-block">\n' +
        '                                <input type="checkbox" name="record" title="笔录制作" checked>\n' +
        '                                <input type="checkbox" name="asr" title="语音识别">\n' +
        '                                <input type="checkbox" name="fd" title="设备控制">\n' +
        '                                <input type="checkbox" name="ph" title="不知道是啥">\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </form>';


    layui.use(['form', 'upload'], function () {
        var form = layui.form;
        var upload = layui.upload;

        var index = layer.open({
            type: 1,
            title: '添加授权',
            content: html,
            area: ['630px', '600px'],
            btn: ['确定', '取消'],
            success: function (layero, index) {
                layero.addClass('layui-form');//添加form标识
                layero.find('.layui-layer-btn0').attr('lay-filter', 'fromContent').attr('lay-submit', '');//将按钮弄成能提交的
                //拖拽上传
                upload.render({
                    elem: '#test10'
                    ,url: '/uploadBytxt/'
                    ,acceptMime: '.txt' //只允许上传图片文件
                    ,exts: 'txt' //只允许上传压缩文件
                    , before: function (obj) {
                        $("#updateCpuCode").val("");
                    }
                    ,done: function(res){
                        //上传成功，把授权码放到指定的name里面
                        console.log(res)

                        if (res.actioncode == "SUCCESS") {
                            $("#updateCpuCode").val(res.data);
                            layer.msg("授权文件加载成功",{icon: 6});
                        }
                    }
                });
                form.render();
            },
            yes: function (index, layero) {
                //自定义验证规则
                form.verify({
                    typename: [/\S/, '请输入问题类型名称'], ordernum: [/\S/, '请输入问题排序号']
                });
                //监听提交
                form.on('submit(fromContent)', function (data) {

                    //提交
                });

            },
            btn2: function (index, layero) {
                layer.close(index);
            }
        });

    });


}