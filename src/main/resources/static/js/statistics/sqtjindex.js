var branchSQCodeCount = [
    {value: 335, name: '公安', selected: true},
    {value: 679, name: '纪委'},
    {value: 1548, name: '监察委'},
    {value: 1548, name: '法院'}
];
var clientSQCodeCount = [
    {value: 335, name: '客户端', selected: true},
    {value: 679, name: '服务器'},
];
var companySQCodeCount = [
    {value: 335, name: '顺泰伟城', selected: true},
    {value: 679, name: '海康威视'},
    {value: 1548, name: '阿里巴巴'}
];
var oemSQCodeCount = [
    {value: 335, name: 'HK', selected: true},
    {value: 679, name: 'NX'},
    {value: 1548, name: '通用'}
];
var typeSQCodeCount = [
    {value: 335, name: '测试', selected: true},
    {value: 679, name: '临时'},
    {value: 1548, name: '销售'}
];
var userSQCodeCount = [
    {value: 335, name: '吴斌 335'},
    {value: 679, name: '彭佩 679'},
    {value: 1548, name: '梁丽颖 1548'}
];
var yearList = ['2020-1','2020-2','2020-3','2020-4','2020-5','2020-6','2020-7','2020-8','2020-9','2020-10','2020-11','2020-12'];
var sqcountList = [23, 42, 18, 45, 48, 49,100,55,66,77,22,88];
var thisisboot;

//获取笔录系统基本信息统计
function getServerBaseStatistics() {
    var url = getactionid_manage().getServerBaseStatistics;
    var data={};
    ajaxSubmitByJson(url, data, callGetServerBaseStatistics);
}

//获取申请人排行榜
function getUsernamePaihb(isboot) {
    var url = getactionid_manage().getUsernamePaihb;
    thisisboot = isboot;
    if(isNotEmpty(isboot)){
        loadIndex = layer.msg("加载中，请稍后...", {
            icon: 16,
            time:10000,
            shade: [0.1,"#fff"]
        });
    }
    var data={};
    ajaxSubmitByJson(url, data, callGetUsernamePaihb);
}

//最近一年授权统计
function getYearStatistics() {
    var url = getactionid_manage().getYearStatistics;
    var data={};
    ajaxSubmitByJson(url, data, callGetYearStatistics);
}

//其他的授权统计
function getElesStatistics() {
    var url = getactionid_manage().getElesStatistics;
    loadIndex = layer.msg("加载中，请稍后...", {
        icon: 16,
        time:10000,
        shade: [0.1,"#fff"]
    });
    var data={};
    ajaxSubmitByJson(url, data, callGetElesStatistics);
}

function callGetServerBaseStatistics(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var biluInfo = data.data;
            $("#branchCount").html(biluInfo.branchCount);
            $("#oemCount").html(biluInfo.oemCount);
            $("#sqAllCount").html(biluInfo.sqAllCount);
            $("#sqgnCount").html(biluInfo.sqgnCount);
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
}

function callGetUsernamePaihb(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)){
            var PaihbList = data.data;

            var ranking_list = "";
            for (let i = 0; i < PaihbList.length; i++) {
                var Paihb = PaihbList[i];
                ranking_list += '<section class="box">\n' +
                    '                                <section class="col_1" title="' + (i + 1) + '">' + (i + 1) + '</section>\n' +
                    '                                <section class="col_2"><img src="/img/statistics/touxiang1.png"  /></section>\n' +
                    '                                <section class="col_3">' + Paihb.name + '</section>\n' +
                    '                                <section class="col_4">' + Paihb.value + '</section>\n' +
                    '                            </section>';

            }

            $("#ranking_list").html(ranking_list);

            $('.box').hover(function() {
                $(this).addClass("cur");
            }, function() {
                $(this).removeClass("cur");
            });
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
    if(isNotEmpty(thisisboot)){
        layer.close(loadIndex);
    }
    thisisboot = null;
}

function callGetYearStatistics(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)) {
            // console.log(data.data);

            var yearDataList = data.data;
            if(null != yearDataList && yearDataList.length > 0){
                yearList = [];
                sqcountList = [];
                for (let i = 0; i < yearDataList.length; i++) {
                    var yearData = yearDataList[i];
                    yearList.push(yearData.year);
                    sqcountList.push(yearData.sqcount);
                }
            }

            //折线图
            var line = echarts.init(document.getElementById('line'));
            line.setOption({
                color:["#32d2c9"],
                title: {
                    x: 'left',
                    text: '最近一年授权统计',
                    textStyle: {
                        fontSize: '18',
                        color: '#4c4c4c',
                        fontWeight: 'bolder'
                    }
                },
                tooltip: {
                    trigger: 'axis'
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        dataView: {readOnly: false},
                        magicType: {type: ['line', 'bar']}
                    }
                },
                xAxis:  {
                    type: 'category',
                    boundaryGap: false,
                    data: yearList,
                    axisLabel: {
                        interval:0
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'授权数量',
                        type:'line',
                        data:sqcountList,
                        markLine: {data: [{type: 'average', name: '平均值'}]}
                    }
                ]
            }) ;

        }
    }else{
        layer.msg(data.message,{icon: 5});
    }

}

function callGetElesStatistics(data){
    if(null!=data&&data.actioncode=='SUCCESS'){
        if (isNotEmpty(data.data)) {
            // console.log(data.data);
            var tongjiAll = data.data;

            branchSQCodeCount = tongjiAll.branchSQCodeCount;
            clientSQCodeCount = tongjiAll.clientSQCodeCount;
            companySQCodeCount = tongjiAll.companySQCodeCount;
            oemSQCodeCount = tongjiAll.oemSQCodeCount;
            typeSQCodeCount = tongjiAll.typeSQCodeCount;
            userSQCodeCount = tongjiAll.userSQCodeCount;

            var branchSQCodeCountHTML = "";
            var clientSQCodeCountHTML = "";
            var companySQCodeCountHTML = "";
            var oemSQCodeCountHTML = "";
            var typeSQCodeCountHTML = "";
            var userSQCodeCountHTML = "";

            $("#chart_area4_tr").html(resSQHTML(branchSQCodeCount, branchSQCodeCountHTML));
            $("#chart_area6_tr").html(resSQHTML(clientSQCodeCount, clientSQCodeCountHTML));
            $("#chart_area2_tr").html(resSQHTML(companySQCodeCount, companySQCodeCountHTML));
            $("#chart_area5_tr").html(resSQHTML(oemSQCodeCount, oemSQCodeCountHTML));
            $("#chart_area3_tr").html(resSQHTML(typeSQCodeCount, typeSQCodeCountHTML));
            $("#chart_area_tr").html(resSQHTML(userSQCodeCount, userSQCodeCountHTML));

            // 使用刚指定的配置项和数据显示图表。
            // 基于准备好的dom，初始化echarts实例
           // var chart_area = echarts.init(document.getElementById('chart_area'));
            /**var option = {
                title: {
                    x: 'left',
                    y: 'top',
                    text: '申请人授权统计(' + userSQCodeCount.length + ')',
                    textStyle: {
                        fontSize: '18',
                        color: '#4c4c4c',
                        fontWeight: 'bolder'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [
                    {
                        name: '授权次数', //内环
                        type: 'pie',
                        selectedMode: 'single', //单一选中模式
                        radius: [0, '67%'], //饼图的半径 [内半径，外半径]

                        label: {
                            normal: {
                                position: 'inner' //内置文本标签
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false	 //不需要设置引导线
                            }
                        },
                        data: userSQCodeCount
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            //chart_area.setOption(option);

            var chart_area2 = echarts.init(document.getElementById('chart_area2'));
            var option2 = {
                title: {
                    x: 'left',
                    y: 'top',
                    text: '公司授权统计(' + companySQCodeCount.length + ')',
                    textStyle: {
                        fontSize: '18',
                        color: '#4c4c4c',
                        fontWeight: 'bolder'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [
                    {
                        name: '授权次数', //内环
                        type: 'pie',
                        selectedMode: 'single', //单一选中模式
                        radius: [0, '67%'], //饼图的半径 [内半径，外半径]

                        label: {
                            normal: {
                                position: 'inner' //内置文本标签
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false	 //不需要设置引导线
                            }
                        },
                        data: companySQCodeCount
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            chart_area2.setOption(option2);

            var chart_area3 = echarts.init(document.getElementById('chart_area3'));
            var option3 = {
                title: {
                    x: 'left',
                    y: 'top',
                    text: '类型授权统计(' + typeSQCodeCount.length + ')',
                    textStyle: {
                        fontSize: '18',
                        color: '#4c4c4c',
                        fontWeight: 'bolder'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [
                    {
                        name: '授权次数', //内环
                        type: 'pie',
                        selectedMode: 'single', //单一选中模式
                        radius: [0, '67%'], //饼图的半径 [内半径，外半径]

                        label: {
                            normal: {
                                position: 'inner' //内置文本标签
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false	 //不需要设置引导线
                            }
                        },
                        data: typeSQCodeCount
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            chart_area3.setOption(option3);

            var chart_area4 = echarts.init(document.getElementById('chart_area4'));
            var option4 = {
                title: {
                    x: 'left',
                    y: 'top',
                    text: '分支版本统计(' + branchSQCodeCount.length + ')',
                    textStyle: {
                        fontSize: '18',
                        color: '#4c4c4c',
                        fontWeight: 'bolder'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [
                    {
                        name: '授权次数', //内环
                        type: 'pie',
                        selectedMode: 'single', //单一选中模式
                        radius: [0, '67%'], //饼图的半径 [内半径，外半径]

                        label: {
                            normal: {
                                position: 'inner' //内置文本标签
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false	 //不需要设置引导线
                            }
                        },
                        data: branchSQCodeCount
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            chart_area4.setOption(option4);

            var chart_area5 = echarts.init(document.getElementById('chart_area5'));
            var option5 = {
                title: {
                    x: 'left',
                    y: 'top',
                    text: 'OEM版本统计(' + oemSQCodeCount.length + ')',
                    textStyle: {
                        fontSize: '18',
                        color: '#4c4c4c',
                        fontWeight: 'bolder'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [
                    {
                        name: '授权次数', //内环
                        type: 'pie',
                        selectedMode: 'single', //单一选中模式
                        radius: [0, '67%'], //饼图的半径 [内半径，外半径]

                        label: {
                            normal: {
                                position: 'inner' //内置文本标签
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false	 //不需要设置引导线
                            }
                        },
                        data: oemSQCodeCount
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            chart_area5.setOption(option5);

            var chart_area6 = echarts.init(document.getElementById('chart_area6'));
            var option6 = {
                title: {
                    x: 'left',
                    y: 'top',
                    text: '客户端统计(' + clientSQCodeCount.length + ')',
                    textStyle: {
                        fontSize: '18',
                        color: '#4c4c4c',
                        fontWeight: 'bolder'
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                series: [
                    {
                        name: '授权次数', //内环
                        type: 'pie',
                        selectedMode: 'single', //单一选中模式
                        radius: [0, '67%'], //饼图的半径 [内半径，外半径]

                        label: {
                            normal: {
                                position: 'inner' //内置文本标签
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false	 //不需要设置引导线
                            }
                        },
                        data: clientSQCodeCount
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            chart_area6.setOption(option6);
             **/
        }
    }else{
        layer.msg(data.message,{icon: 5});
    }
    layer.close(loadIndex);
}


function resSQHTML(arrs, SQHTML) {
    if(arrs.length > 0){
        for (let i = 0; i < arrs.length; i++) {
            var usersq = arrs[i];
            SQHTML += '<tr>\n' +
                '                         <td>' + usersq.name + '</td>\n' +
                '                         <td>' + usersq.value + '</td>\n' +
                '                   </tr>\n';
        }
    }
    return SQHTML;
}