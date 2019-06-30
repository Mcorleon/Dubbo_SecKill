/**
 * Created by mcorl on 2019/2/22.
 */
$(function () {
    var goods_id = getUrlParam("id");
    //获取当前session下的用户
    $.ajax({
        type: "get",
        url: "/user/getCurrentUser",
        data: {},
        dataType: "json",
        success: function (data) {

            if (data == null) {
                window.location.href = "/login";
            } else {
                console.log(data);
                $("#p1").text("欢迎," + data.nickname);
            }
        },
        error: function () {
            window.location.href = "/login";
            console.log("ajax error");
        }
    });
    layui.use(['layer', 'form', 'table'], function () {
        var form = layui.form,
            layer = layui.layer,
            table = layui.table;
        //表单
        form.render();
        table.render({
            elem: '#tb',
            method: 'post',
            url: '/goods/getMiaoshaGoodsList/' + goods_id,
            limits: [5, 10],
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": res.data //解析数据列表
                }
            }
            , cols: [[
                {field: 'id', title: '秒杀商品编号', width: 168, align: 'center'}
                , {field: 'name', title: '商品名称', width: 150, align: 'center'}
                , {field: 'img', title: '图片', width: 200, align: 'center', templet: '<div><img src="{{d.img}}"></div>'}
                , {title: '开始时间', width: 180, align: 'center', templet: '#start_time'}
                , {title: '结束时间', width: 180, align: 'center', templet: '#end_time'}
                , {title: '剩余时间', width: 180, align: 'center', templet: '#countDown'}
                , {field: 'price', title: '原价', width: 100, align: 'center'}
                , {field: 'miaosha_price', title: '秒杀价', width: 100, align: 'center'}
                , {field: 'stock', width: 100, title: '库存', align: 'center'}
                , {title: '操作', toolbar: '#barDemo', width: 101, align: 'center'}
            ]]
            , page: true
        });
        table.on('tool(tb)', function (obj) {
            var data = obj.data;
            console.log(obj.data);
            if (obj.event === 'buy') {
                var endtime = data.end_time;
                var stock = data.stock;
                var startTime = data.start_time;
                var current_time = new Date().getTime(); //设定当前时间
                var time_end = new Date(endtime).getTime(); //设定目标时间
                var time_start = new Date(startTime).getTime(); //设定目标时间
                if(current_time-time_start<0){
                    layer.msg("活动未开始");
                }else if(current_time-time_end>0){
                    layer.msg("活动已经结束");
                }else if(stock<1){
                    layer.msg("库存不足");
                } else {
                    window.location.href = "/checkOrder?miaosha_id="+data.id;
                }
            }
        });
    });
    show_time();


})

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]);
    return null; //返回参数值
}
//倒计时
function show_time() {
    var endtime = $("#endTime").prop("title");
    var startTime = $("#startTime").prop("title");
    var current_time = new Date().getTime(); //设定当前时间
    var time_end = new Date(endtime).getTime(); //设定目标时间
    var time_start = new Date(startTime).getTime(); //设定目标时间
    var time_distance;
    var timer;

    if (current_time - time_start < 0) {
        //还没开始
        time_distance = time_start - current_time;
        var int_day = Math.floor(time_distance / 86400000)
        time_distance -= int_day * 86400000;
        var int_hour = Math.floor(time_distance / 3600000)
        time_distance -= int_hour * 3600000;
        var int_minute = Math.floor(time_distance / 60000)
        time_distance -= int_minute * 60000;
        var int_second = Math.floor(time_distance / 1000)
        time_distance -= int_second * 1000;
        var int_mil_second = Math.floor(time_distance / 1)
        if (int_day < 10) {
            int_day = "0" + int_day;
        }
        if (int_hour < 10) {
            int_hour = "0" + int_hour;
        }
        if (int_minute < 10) {
            int_minute = "0" + int_minute;
        }
        if (int_second < 10) {
            int_second = "0" + int_second;
        }
        if (int_mil_second < 100) {
            if (int_mil_second < 10) {
                int_mil_second = "00" + int_mil_second;
            }
            int_mil_second = "0" + int_mil_second;
        }
        $("#sp1").html("距离活动开始还有" + int_day + "天" + int_hour + "时" + int_minute + "分" + int_second + "秒:"+int_mil_second);
    } else {
        time_distance = time_end - current_time;
        if (time_distance >= 0) {
            var int_day = Math.floor(time_distance / 86400000)
            time_distance -= int_day * 86400000;
            var int_hour = Math.floor(time_distance / 3600000)
            time_distance -= int_hour * 3600000;
            var int_minute = Math.floor(time_distance / 60000)
            time_distance -= int_minute * 60000;
            var int_second = Math.floor(time_distance / 1000)
            time_distance -= int_second * 1000;
            var int_mil_second = Math.floor(time_distance / 1)
            if (int_day < 10) {
                int_day = "0" + int_day;
            }
            if (int_hour < 10) {
                int_hour = "0" + int_hour;
            }
            if (int_minute < 10) {
                int_minute = "0" + int_minute;
            }
            if (int_second < 10) {
                int_second = "0" + int_second;
            }
            if (int_mil_second < 100) {
                if (int_mil_second < 10) {
                    int_mil_second = "00" + int_mil_second;
                }
                int_mil_second = "0" + int_mil_second;
            }
            $("#sp1").html(int_day + "天" + int_hour + "时" + int_minute + "分" + int_second + "秒:"+int_mil_second);
        } else {
            $("#sp1").html('00天00时00分00秒');
        }
    }


    timer = setTimeout(function () {
        show_time();
    }, 100);
}



