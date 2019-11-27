var loadIndex;

function login_login(){

    var url = getactionid_manage().loginCaChe;

    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:10000,
        shade: [0.1,"#fff"]
    });

    var loginaccount =$('input[name="loginaccount"]').val();
    var password =$('input[name="password"]').val();
    var data={
        loginaccount:loginaccount,
        password:password
    };
    ajaxSubmitByJson(url,data,callLogin_login);

}

function callLogin_login(data){
    layer.close(loadIndex);//关闭load特效
    if(null!=data&&data.actioncode=='SUCCESS'){
        var url = getactionid_manage().admin_index;
        window.location.href = url;
    }else{
        layer.msg(data.message, {icon: 5});
        // alert(data.message);
    }
    // layui.use('form', function(){
    //     var form = layui.form;
    //     form.render();
    // });
}

function login_logout(){

    var url = getactionid_manage().main_logout;

    ajaxSubmitByJson(url,null,callLogout);
}

function callLogout(data){
    layer.close(loadIndex);//关闭load特效
    if(null!=data&&data.actioncode=='SUCCESS'){
        //alert(data.message);
        // var url=getActionURL(getactionid_manage().login_main);
        window.location.href="/";
    }else{
        alert(data.message);
        // layer.msg(data.message, {time: 5000, icon:6});
    }

}


